package com.example.dogwhistle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.dogwhistle.databinding.ActivityMainBinding
import com.github.nisrulz.zentone.ZenTone
import com.github.nisrulz.zentone.wave_generators.SineWaveGenerator
import com.marcinmoskala.arcseekbar.ProgressListener
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val zenTone = ZenTone()
    private var freq: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCallBacks()
    }

    private fun addCallBacks() {
        with(binding) {

            seekArc.setProgressGradient(Color.WHITE, Color.BLUE)
            seekArc.onProgressChangedListener = ProgressListener {
                stopSound()
                freq = (((25 * it / 100.0) * 10.0).roundToInt() / 10.0f)
                textViewHertz.text = freq.toString().plus(" kHz")
            }


            buttonPlayTone.setOnClickListener {
                togglePlay()
            }
        }
    }

    private fun togglePlay() {
        if (zenTone.isPlaying) {
            stopSound()
        } else {
            playSound()
        }
    }

    private fun playSound() {
        with(binding) {
            buttonPlayTone.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.red
                )
            )
            buttonPlayTone.text = getString(R.string.stop)
        }
        zenTone.play(freq * 1000, 100, SineWaveGenerator)
    }

    private fun stopSound() {
        with(binding) {
            buttonPlayTone.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.blue
                )
            )
            buttonPlayTone.text = getString(R.string.play)
        }
        zenTone.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        zenTone.release()
    }
}
