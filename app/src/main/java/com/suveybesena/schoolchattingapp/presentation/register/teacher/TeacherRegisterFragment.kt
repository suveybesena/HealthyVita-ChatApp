package com.suveybesena.schoolchattingapp.presentation.register.teacher

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.suveybesena.schoolchattingapp.R
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.databinding.FragmentTeacherRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherRegisterFragment : Fragment() {

    private lateinit var contentResolver: ContentResolver
    var pickedImage: Uri? = null
    var bitmap: Bitmap? = null
    private val viewModel: TeacherRegisterViewModel by viewModels()

    private var binding: FragmentTeacherRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherRegisterBinding.inflate(inflater)

        val fields = resources.getStringArray(R.array.Fields)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, fields)
        binding?.tvAutoComplete?.setAdapter(arrayAdapter)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentResolver = requireActivity().contentResolver

        initListeners()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel._uiState.collect { state ->
                state.isRegister.let { isRegister ->
                    if (isRegister == true) {
                        goTeachersFragment()
                    }
                }
                state.error.let { error ->
                    Snackbar.make(requireView(), "giriş yapılamadı", Snackbar.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun initListeners() {
        binding?.apply {
            bvSignUp.setOnClickListener {
                signUp()
            }
            ivProfile.setOnClickListener {
                ivPickedImage()
            }
            bvSignIn.setOnClickListener {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
      findNavController().navigate(R.id.action_teacherRegisterFragment_to_loginFragment)
    }

    private fun goTeachersFragment() {
        findNavController().navigate(R.id.action_teacherRegisterFragment_to_teachersFragment)
    }

    private fun signUp() {
        binding?.apply {
            pickedImage?.let { uri ->
                val authInfo =
                    RegisterModel(
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        etUserName.text.toString(),
                        uri
                    )
                if (etPassword.text.toString() == etPasswordConfirm.text.toString()) {
                    viewModel.handleEvent(TeachersRegisterEvent.CreateUser(authInfo), tvAutoComplete.text.toString())

                } else {
                    Snackbar.make(requireView(), "Şifreleriniz eşleşmiyor.", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun ivPickedImage() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {

            pickedImage = data.data

            if (pickedImage != null) {

                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver, pickedImage!!)
                    bitmap = ImageDecoder.decodeBitmap(source)
                    binding?.ivProfile?.setImageBitmap(bitmap)

                } else {
                    bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, pickedImage)
                    binding?.ivProfile?.setImageBitmap(bitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}