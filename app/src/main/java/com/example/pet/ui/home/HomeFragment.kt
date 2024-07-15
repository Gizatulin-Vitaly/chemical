package com.example.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pet.databinding.FragmentHomeBinding
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.pet.ui.home.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fun openGunAnimate() {

            // Получаем ссылки на нужные элементы из разметки
            val groupChangeVoltage = binding.changeVoltageLayout.groupChangeVoltage
            val voltageShevron = binding.changeVoltageLayout.voltageShevron
            val rotateOpenAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_open)
            val rotateCloseAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_close)


            // Получаем родительский контейнер и устанавливаем длительность анимации
            val container1 = groupChangeVoltage.parent as View
            val resources = resources
            val displayMetrics = resources.displayMetrics
            val density = displayMetrics.density
            val duration = 500L

            if (groupChangeVoltage.visibility == View.VISIBLE) {
                voltageShevron.startAnimation(rotateCloseAnim)
                val anim = ValueAnimator.ofInt(container1.height, (130/ density).toInt())
                anim.duration = duration
                anim.addUpdateListener { valueAnimator ->
                    val value =
                        valueAnimator.animatedValue as Int
                    val layoutParams = container1.layoutParams
                    layoutParams.height = value
                    container1.layoutParams = layoutParams
                }
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        groupChangeVoltage.visibility = View.GONE
                    }
                })
                anim.start()
            } else {
                voltageShevron.startAnimation(rotateOpenAnim)
                container1.post {
                    val anim = ValueAnimator.ofInt((130/ density).toInt(), (550/ density).toInt())
                    anim.duration = duration
                    anim.addUpdateListener { valueAnimator ->
                        val value =
                            valueAnimator.animatedValue as Int
                        val layoutParams = container1.layoutParams
                        layoutParams.height = value
                        container1.layoutParams = layoutParams
                        groupChangeVoltage.visibility = View.VISIBLE
                    }
                    anim.start()
                }
            }
        }

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//          Устанавливаем высоту тени кнопки
        val resultButton = binding.resultButton
        resultButton.elevation = 12.0f


        var defoltCaltulate = 0

        val electroConstraintLayout = binding
            .inputMaterialCard.electroConstraint
        val scaleConstraintLayout = binding
            .inputMaterialCard.scaleConstrain
        electroConstraintLayout.isSelected = true

        electroConstraintLayout.setOnClickListener {
            electroConstraintLayout.isSelected = true
            scaleConstraintLayout.isSelected = false
            defoltCaltulate = 0
            val textInputLayout = binding.inputUsers
            textInputLayout.editText?.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.speed,
                0
            )
        }

        scaleConstraintLayout.setOnClickListener {
            electroConstraintLayout.isSelected = false
            scaleConstraintLayout.isSelected = true
            defoltCaltulate = 1
            val textInputLayout = binding.inputUsers
            textInputLayout.editText?.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.electric_bolt,
                0
            )
        }


        val voltageString05 = "0 - 5 mA"
        val voltageString420 = "4 - 20 mA"
        var defoltVoltage = 0

        binding.changeVoltageLayout.selectVoltage.setOnClickListener {
            openGunAnimate()
        }
        binding.changeVoltageLayout.voltageShevron.setOnClickListener {
            openGunAnimate()
        }
        binding.changeVoltageLayout.scoreSelectVoltage.setOnClickListener {
            openGunAnimate()
        }

        binding.changeVoltageLayout.constraintVoltage05.setOnClickListener {
            binding.changeVoltageLayout.scoreSelectVoltage.text = voltageString05
            openGunAnimate()
            defoltVoltage = 0
        }
        binding.changeVoltageLayout.constraintVoltage420.setOnClickListener {
            binding.changeVoltageLayout.scoreSelectVoltage.text = voltageString420
            openGunAnimate()
            defoltVoltage = 1
        }

        binding.resultButton.setOnClickListener {

//            Получаем Вводимые значения
            val userValueText = binding.inputUsersText.text.toString().trim()
            val startScaleSensText =
                binding.inputMaterialCard.inputStartSensorText.text.toString().trim()
            val endScaleSensText =
                binding.inputMaterialCard.inputEndSensorText.text.toString().trim()

//            Проверяем на пустоту
            if (userValueText.isEmpty()) {
                val colorStateList =
                    ContextCompat.getColorStateList(requireContext(), R.color.coral)
                binding.inputUsers.setBoxStrokeErrorColor(colorStateList)
                binding.inputUsers.error = "Заполните это поле"
            } else {
                val defaultColorStateList =
                    ResourcesCompat.getColorStateList(resources, R.color.durty_white, null)
                binding.inputUsers.setBoxStrokeErrorColor(defaultColorStateList)
                binding.inputUsers.error = null
            }

            if (startScaleSensText.isEmpty()) {
                binding.inputMaterialCard.inputStartSensor.boxStrokeErrorColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.coral)
                binding.inputMaterialCard.inputStartSensor.error = "Заполните это поле"
            } else {
                binding.inputMaterialCard.inputStartSensor.boxStrokeErrorColor = null
                binding.inputMaterialCard.inputStartSensor.error = null
                binding.inputMaterialCard.inputStartSensor.layoutParams.height =
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            }


            if (endScaleSensText.isEmpty()) {
                binding.inputMaterialCard.inputEndSensor.boxStrokeErrorColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.coral)
                binding.inputMaterialCard.inputEndSensor.error = "Заполните это поле"
            } else {
                binding.inputMaterialCard.inputEndSensor.boxStrokeErrorColor = null
                binding.inputMaterialCard.inputEndSensor.error = null
                binding.inputMaterialCard.inputEndSensor.layoutParams.height =
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            }


            val userValue = userValueText.toFloatOrNull() ?: 0f
            val startScaleSens = startScaleSensText.toIntOrNull() ?: 0
            val endScaleSens = endScaleSensText.toIntOrNull() ?: 0

            fun elecron(min: Int, max: Int): String {
                return if (endScaleSens - startScaleSens != 0) {
                    val result =
                        ((userValue - startScaleSens) * (max - min)) / (endScaleSens - startScaleSens) + min
                    result.toString()
                } else {
                    "Деление на ноль"
                }
            }

            fun sensor(min: Int, max: Int): String {
                return if (max - min != 0) {
                    val result =
                        ((userValue - min) * (endScaleSens - startScaleSens)) / (max - min) + startScaleSens
                    result.toString()
                } else {
                    "Деление на ноль"
                }
            }

            val resultTextView: String = if (defoltCaltulate == 1) {
                if (defoltVoltage == 0) {
                    sensor(0, 5)
                } else {
                    sensor(4, 20)
                }
            } else {
                if (defoltVoltage == 0) {
                    elecron(0, 5)
                } else {
                    elecron(4, 20)
                }
            }



            if (startScaleSensText.isEmpty() || endScaleSensText.isEmpty() || userValueText.isEmpty()) {
                binding.resultTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.coral
                    )
                )
                binding.resultTextView.text = "Остались пустые значения"
            } else {
                binding.resultTextView.text = String.format("%.3f", resultTextView.toDouble())
                binding.resultTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.durty_white
                    )
                )
            }
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}