import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fon.Contact

@Composable
fun ContactListItem(contact: Contact) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = contact.name, fontSize = 16.sp)
        Text(text = contact.phoneNumber, fontSize = 14.sp)
    }
}
