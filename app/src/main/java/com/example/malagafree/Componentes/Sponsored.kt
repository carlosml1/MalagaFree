package com.example.malagafree.Componentes

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.carlosml.malagafree.R


class Sponsored : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsored)

        ModoInversivo.setImmersiveMode(this)

        val btnVolver : ImageButton = findViewById(R.id.btnVolverSponsored)

        btnVolver.setOnClickListener { onBackPressed() }

        val almafilms: ImageButton = findViewById(R.id.almafilms)

        almafilms.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://almafilms.es/")
            startActivity(intent);
        }
    }
}