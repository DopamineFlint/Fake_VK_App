package com.example.fakevkappreborn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.fakevkappreborn.sign_in_model.SignInModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        private const val KEY_SAVED_INSTANT_SIGN_IN_MODEL = "sign_in_model"
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }

    private var signInModel: SignInModel = SignInModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainMenuFragment: Fragment = MainMenuFragment()

        vk_edit_text_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signInModel.email = vk_edit_text_email.text.toString().trim()

                vk_sign_in_button.isEnabled = isValidSim()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        vk_edit_text_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signInModel.password = vk_edit_text_password.text.toString().trim()

                vk_sign_in_button.isEnabled = isValidSim()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        // TODO: А так же добавить плавную анимацию!!!
        vk_sign_in_button.setOnClickListener {
            if (signInModel.checkEmailAndPassword()) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, mainMenuFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            } else {
                Toast.makeText(activity, "Email or password are incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isValidSim(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(signInModel.email)
            .matches() && (signInModel.password.length >= 8)
    }

    /*override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        signInModel = savedInstanceState.getParcelable(KEY_SAVED_INSTANT_SIGN_IN_MODEL)!!
        Log.d("myLog", "${signInModel.email} ${signInModel.password}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(KEY_SAVED_INSTANT_SIGN_IN_MODEL, signInModel)
    }*/
}