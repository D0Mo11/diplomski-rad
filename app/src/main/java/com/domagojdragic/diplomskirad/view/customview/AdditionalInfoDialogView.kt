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

private const val NAME = "name"
private const val LINK = "link"
private const val FIRE_POINTS = "fire_points"
private const val SMOKE_POINTS = "smoke_points"
private const val SPACE_TYPE = "space_type"
private const val ROOM_TYPE = "room_type"
private const val TIME_OF_DAY = "time_of_day"
private const val FLAME_SIZE = "flame_size"
private const val DOCUMENT_NAME = "fire-images"

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
        val firestoreDB = FirebaseFirestore.getInstance().document("$DOCUMENT_NAME/$imageName")

        binding.btnSave.setOnClickListener {
            val spaceType = getSpaceType(binding.groupSpaceType.checkedRadioButtonId)
            val roomType = getRoomType(binding.groupRoomType.checkedRadioButtonId)
            val timeOfDay = getTimeOfDay(binding.groupTimeOfDay.checkedRadioButtonId)
            val flameSize = getFlameSize(binding.groupFlameSize.checkedRadioButtonId)
            val dataToSave = HashMap<String, Any>()

            dataToSave[NAME] = imageName
            dataToSave[LINK] = currentImage.selfLink
            dataToSave[FIRE_POINTS] = fireShapes.flatten()
            dataToSave[SMOKE_POINTS] = smokeShapes.flatten()
            dataToSave[SPACE_TYPE] = spaceType
            dataToSave[ROOM_TYPE] = roomType
            dataToSave[TIME_OF_DAY] = timeOfDay
            dataToSave[FLAME_SIZE] = flameSize

            firestoreDB.set(dataToSave).addOnSuccessListener {
                viewModel.updateIsComplete(currentImage)
                onSave()
                popupWindow.dismiss()
                Toast.makeText(context, context.getString(R.string.firestore_save_success_message), Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                popupWindow.dismiss()
                Toast.makeText(context, context.getString(R.string.firestore_save_failure_message), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun getSpaceType(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_indoor -> context.getString(R.string.indoor_save)
            R.id.btn_outdoor -> context.getString(R.string.outdoor_save)
            R.id.btn_unknown_space_type -> context.getString(R.string.unknown_save)
            else -> context.getString(R.string.not_selected)
        }
    }

    private fun getRoomType(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_warehouse -> context.getString(R.string.warehouse_save)
            R.id.btn_office -> context.getString(R.string.office_save)
            R.id.btn_building -> context.getString(R.string.building_save)
            else -> context.getString(R.string.not_selected)
        }
    }

    private fun getTimeOfDay(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_daytime -> context.getString(R.string.daytime_save)
            R.id.btn_nighttime -> context.getString(R.string.nighttime_save)
            R.id.btn_sunrise_sunset -> context.getString(R.string.sunrise_sunset_save)
            R.id.btn_unknown_daytime -> context.getString(R.string.unknown_save)
            else -> context.getString(R.string.not_selected)
        }
    }

    private fun getFlameSize(buttonId: Int): String {
        return when (buttonId) {
            R.id.btn_small -> context.getString(R.string.small_save)
            R.id.btn_medium -> context.getString(R.string.medium_save)
            R.id.btn_large -> context.getString(R.string.large_save)
            R.id.btn_none -> context.getString(R.string.none_save)
            else -> context.getString(R.string.not_selected)
        }
    }
}
