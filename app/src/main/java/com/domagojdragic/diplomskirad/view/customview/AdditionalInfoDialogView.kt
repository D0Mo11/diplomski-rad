package com.domagojdragic.diplomskirad.view.customview

import android.content.Context
import android.graphics.PointF
import android.view.LayoutInflater
import android.widget.Toast
import com.domagojdragic.diplomskirad.R
import com.domagojdragic.diplomskirad.databinding.DialogAdditionalInfoBinding
import com.domagojdragic.diplomskirad.model.entity.ImageEntity
import com.domagojdragic.diplomskirad.viewmodel.AnnotationViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AdditionalInfoDialogView(private val context: Context) : BaseDialogView<DialogAdditionalInfoBinding>(
    DialogAdditionalInfoBinding.inflate(LayoutInflater.from(context))
) {

    fun render(
        smokeShapes: List<List<PointF>>,
        fireShapes: List<List<PointF>>,
        viewModel: AnnotationViewModel,
        currentImage: ImageEntity,
        onSave: () -> Unit,
    ) {
        val imageName = currentImage.imageName.substringAfter("/")
        val firestoreDB = FirebaseFirestore.getInstance().document("fire-images/$imageName")
        binding.btnSave.setOnClickListener {
            val spaceType = getSpaceType(binding.groupSpaceType.checkedRadioButtonId)
            val roomType = getRoomType(binding.groupRoomType.checkedRadioButtonId)
            val timeOfDay = getTimeOfDay(binding.groupTimeOfDay.checkedRadioButtonId)
            val flameSize = getFlameSize(binding.groupFlameSize.checkedRadioButtonId)
            val dataToSave = HashMap<String, Any>()

            dataToSave["name"] = imageName
            dataToSave["link"] = currentImage.selfLink
            dataToSave["fire_shapes"] = fireShapes.flatten()
            dataToSave["smoke_shapes"] = smokeShapes.flatten()
            dataToSave["space_type"] = spaceType
            dataToSave["room_type"] = roomType
            dataToSave["time_of_day"] = timeOfDay
            dataToSave["flame_size"] = flameSize

            firestoreDB.set(dataToSave).addOnSuccessListener {
                viewModel.updateIsComplete(currentImage)
                onSave()
                popupWindow.dismiss()
                Toast.makeText(context, "Successfully saved image annotation to firestore!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                popupWindow.dismiss()
                Toast.makeText(context, "Error saving image annotation to firestore! Please try again", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancel.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun getSpaceType(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_indoor -> "Indoor"
            R.id.btn_outdoor -> "Outdoor"
            R.id.btn_unknown_space_type -> "Unknown"
            else -> "Not selected"
        }
    }

    private fun getRoomType(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_warehouse -> "Warehouse"
            R.id.btn_office -> "Office"
            R.id.btn_building -> "Building"
            else -> "Not selected"
        }
    }

    private fun getTimeOfDay(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_daytime -> "Daytime"
            R.id.btn_nighttime -> "Nigthttime"
            R.id.btn_sunrise_sunset -> "Sunrise/Sunset"
            R.id.btn_unknown_daytime -> "Unknown"
            else -> "Not selected"
        }
    }

    private fun getFlameSize(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_small -> "Small"
            R.id.btn_medium -> "Medium"
            R.id.btn_large -> "Large"
            R.id.btn_none -> "None"
            else -> "Not selected"
        }
    }
}
