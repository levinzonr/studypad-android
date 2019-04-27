package cz.levinzonr.studypad.presentation.screens.profile.edit
import android.os.Bundle
import android.view.*
import cz.levinzonr.studypad.R
import cz.levinzonr.studypad.loadAuthorImage
import cz.levinzonr.studypad.observeNonNull
import cz.levinzonr.studypad.onTextChanged
import cz.levinzonr.studypad.presentation.base.BaseFragment
import cz.levinzonr.studypad.presentation.screens.onboarding.signup.UniversitySelectorFragment
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class EditProfileFragment : BaseFragment() {

    override val viewModel: EditProfileViewModel by viewModel()
    private lateinit var saveMenuItem: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfileNameEt.onTextChanged {
            viewModel.onDisplayNameChanged(it)
        }

        editProfileSchoolEt.setOnClickListener {
            UniversitySelectorFragment.show(childFragmentManager) {
                viewModel.onUniversityChanged(it)
                editProfileSchoolEt.setText(it.fullName)
            }
        }

    }

    override fun subscribe() {
        viewModel.profileLiveData.observeNonNull(viewLifecycleOwner) {
            editProfileNameEt.setText(it.displayName)
            editProfileSchoolEt.setText(it.university?.fullName)
            editProfileIv.loadAuthorImage(it.imageUrl)
        }


        viewModel.stateLiveData.observeNonNull(viewLifecycleOwner) {
            Timber.d("state ${it.isSaveEnabled}")
            saveMenuItem.setVisible(it.isSaveEnabled)
        }
    }

    override fun showLoading(isLoading: Boolean) {
        progressDialog?.getMessageTextView()?.text = "Applying changes..."
        if (isLoading) progressDialog?.show() else progressDialog?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit_profile, menu)
        saveMenuItem = menu.findItem(R.id.editProfileSaveBtn)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.editProfileSaveBtn -> {
                viewModel.onSaveProfileButtonClicked()
                true
            }
            else -> true
        }
    }



}
