package id.hikmah.binar.challenge6.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import id.hikmah.binar.challenge6.LoginRegActivity
import id.hikmah.binar.challenge6.R
import id.hikmah.binar.challenge6.database.ProfilEntity
import id.hikmah.binar.challenge6.databinding.FragmentProfilBinding
import id.hikmah.binar.challenge6.repo.DataStoreRepo
import id.hikmah.binar.challenge6.repo.UserRepo
import id.hikmah.binar.challenge6.viewModelsFactory
import id.hikmah.binar.challenge6.viewmodel.DataStoreViewModel
import id.hikmah.binar.challenge6.viewmodel.ProfileViewModel
import java.io.ByteArrayOutputStream

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val pref: DataStoreRepo by lazy { DataStoreRepo(requireContext()) }
    private val datastoreViewModel: DataStoreViewModel by viewModelsFactory { DataStoreViewModel(pref) }

    private val userRepo: UserRepo by lazy { UserRepo(requireContext()) }
    private val profileViewModel: ProfileViewModel by viewModelsFactory { ProfileViewModel(userRepo) }

    companion object {
        const val REQUEST_CODE_PERMISSION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doLogout()
        getUserDetail()
        updateUserDetail()
        observeData()
        setImageProfile()
    }

    private fun doLogout() {
        binding.btnLogout.setOnClickListener {
            datastoreViewModel.deleteAllData()
            startActivity(Intent(requireContext(), LoginRegActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun getUserDetail() {
        datastoreViewModel.getUsername().observe(viewLifecycleOwner) {
            profileViewModel.getUserDetail(it)
        }
    }

    private fun updateUserDetail() {
        binding.btnUpdate.setOnClickListener {
            // Get value dari EditText
            val etUsername = binding.editUsername.text.toString()
            val etNamaLengkap = binding.editNamalengkap.text.toString()
            val etTglLahir = binding.editTglLahir.text.toString()
            val etAlamat = binding.editAlamat.text.toString()

            val userdet = ProfilEntity(null, etUsername, etNamaLengkap, etTglLahir, etAlamat)
            profileViewModel.updateUserDetail(etUsername, etNamaLengkap, etTglLahir, etAlamat, userdet)
        }
    }

    private fun observeData() {
        profileViewModel.usernameDetail.observe(viewLifecycleOwner) {
            binding.editUsername.setText(it)
        }
        profileViewModel.namaLengkapDetail.observe(viewLifecycleOwner) {
            binding.editNamalengkap.setText(it)
        }
        profileViewModel.tglLahirDetail.observe(viewLifecycleOwner) {
            binding.editTglLahir.setText(it)
        }
        profileViewModel.alamatDetail.observe(viewLifecycleOwner) {
            binding.editAlamat.setText(it)
        }

        profileViewModel.statusUpdate.observe(viewLifecycleOwner) {
            if (it == false) {
                Toast.makeText(requireContext(), "Gagal Update", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Berhasil Update", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setImageProfile() {
        observeImage()
        binding.imgProfil.setOnClickListener {
            checkingPermission()
        }
    }

    private fun observeImage() {
        datastoreViewModel.getImage().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val index = it.substring(0,1)
                when (index) {
                    "1" -> {
                        val data = it.substringAfter("_")
                        val uri = Uri.parse(data)
                        binding.imgProfil.setImageURI(uri)
                    }
                    "2" -> {
                        val data = it.substringAfter("_")
                        val bitmap = stringToBitMap(data)
                        binding.imgProfil.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun checkingPermission() {
        // apakah permission sudah di setujui atau belum
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // klau udah di tolak sebelumnya
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            }
            // klau belum pernah di tolak (request pertama kali)
            else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    // dialoag yg muncul kalau user menolak permission yg di butuhkan
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                // mengarahkan user untuk buka halaman setting
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    // buat buka gallery
    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    // buat dapetin URI image gallery
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            // simpan uri ke dlm data store
            var uriString = result.toString()
            uriString = "1_$uriString"
            datastoreViewModel.saveImage(uriString)
            // munculin image dari gallery ke ImageView
            binding.imgProfil.setImageURI(result)
        }

    // buat open camera
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    // buat dapetin bitmap image dari camera
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                // dapetin data bitmap dari intent
                val bitmap = result.data!!.extras?.get("data") as Bitmap

                // simpan bitmap ke dlm data store
                var bitmapString = bitMapToString(bitmap)
                bitmapString = "2_$bitmapString"
                datastoreViewModel.saveImage(bitmapString)
                // load bitmap ke dalam imageView
                binding.imgProfil.setImageBitmap(bitmap)
            }
        }

    private fun bitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }
}