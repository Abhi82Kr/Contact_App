
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactapp.Data.Contact
import com.google.firebase.database.*

class ContactViewModel : ViewModel() {



    // Firebase Realtime Database reference
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("contacts")

    // LiveData to hold the contact list
    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> get() = _contactsList


    init {
        getContacts()
    }

    // Function to add a new contact with an auto-generated ID
    fun addContact(context: Context,name: String, phone: String, email: String) {
        // Generate a unique key (ID) using Firebase's push() method
        val contactId = dbRef.push().key ?: return
        val contact = Contact(contactId, name, phone, email)

        dbRef.child(contactId).setValue(contact)
            .addOnSuccessListener {
                // Handle success
                //println("Contact added successfully")

                Toast.makeText(context,"Contact Added Successfully",Toast.LENGTH_SHORT)
            }
            .addOnFailureListener {
                // Handle failure
                println("Failed to add contact: ${it.message}")
            }
    }

    // Function to edit a contact
    fun editContact(contactId: String, name: String, phone: String, email: String) {
        val contactUpdates = mapOf<String, Any>(
            "name" to name,
            "phone" to phone,
            "email" to email
        )
        dbRef.child(contactId).updateChildren(contactUpdates)
            .addOnSuccessListener {
                // Handle success
                println("Contact updated successfully")
            }
            .addOnFailureListener {
                // Handle failure
                println("Failed to update contact: ${it.message}")
            }
    }

    // Function to delete a contact
    fun deleteContact(contactId: String) {
        dbRef.child(contactId).removeValue()
            .addOnSuccessListener {
                // Handle success
                println("Contact deleted successfully")
            }
            .addOnFailureListener {
                // Handle failure
                println("Failed to delete contact: ${it.message}")
            }
    }

    // Function to get the list of contacts and update LiveData
    fun getContacts() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val contacts = mutableListOf<Contact>()
                for (contactSnapshot in snapshot.children) {
                    val contact = contactSnapshot.getValue(Contact::class.java)
                    contact?.let { contacts.add(it) }
                }
                // Update LiveData with the retrieved contacts
                _contactsList.value = contacts
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle failure to retrieve contacts
                println("Failed to load contacts: ${error.message}")
            }
        })
    }

    // LiveData to hold the retrieved contact
    private val _contact = MutableLiveData<Contact?>()
    val contact: LiveData<Contact?> get() = _contact


    // Function to get a contact by ID
    fun getContactById(contactId: String) {
        dbRef.child(contactId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // Retrieve the contact object
                val retrievedContact = snapshot.getValue(Contact::class.java)
                _contact.value = retrievedContact
            } else {
                _contact.value = null // Handle case where contact doesn't exist
            }
        }.addOnFailureListener { exception ->
            // Handle the error, you can also set _contact to null or log the error
//            Log.e("ContactViewModel", "Error retrieving contact: ${exception.message}")
            _contact.value = null
        }
    }
}
