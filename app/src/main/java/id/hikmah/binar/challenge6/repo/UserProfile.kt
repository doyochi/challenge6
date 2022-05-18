package id.hikmah.binar.challenge6.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserProfile (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "nama_lengkap") val nama_lengkap: String?,
    @ColumnInfo(name = "tgl_lahir") val tgl_lahir: String?,
    @ColumnInfo(name = "alamat") val alamat: String?
)