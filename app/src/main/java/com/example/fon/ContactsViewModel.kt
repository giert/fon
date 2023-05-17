import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fon.Contact

class ContactsViewModel : ViewModel() {
    private val _contacts = mutableStateOf(listOf<Contact>())
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
}
