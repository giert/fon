import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fon.Contact

class ContactsViewModel : ViewModel() {
    private val _contacts = MutableLiveData(listOf<Contact>())
    val contacts = _contacts

    init {
        // Add some sample contacts for demonstration
        val sampleContacts = listOf(
            Contact(1, "John Doe", "123-456-7890"),
            Contact(2, "Jane Smith", "234-567-8901"),
            Contact(3, "Mike Johnson", "345-678-9012")
        )
        _contacts.value = sampleContacts
    }

    fun fetchContacts(context: Context) {
        val contacts = mutableListOf<Contact>()
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        if (cursor != null && cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            do {
                val id = if (idIndex >= 0) cursor.getInt(idIndex) else -1
                val name = if (nameIndex >= 0) cursor.getString(nameIndex) else "Unknown"
                val phoneNumber = if (phoneNumberIndex >= 0) cursor.getString(phoneNumberIndex) else ""

                contacts.add(Contact(id, name, phoneNumber))
            } while (cursor.moveToNext())

            cursor.close()
        }

        _contacts.value = contacts
    }

}
