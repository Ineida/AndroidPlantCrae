package com.projectPlant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//@Todo: page d'accueil avec la liste des plants et leurs etat actuel et une petit information sur la meteo du jour cette activité aura des tabs pour trasiter entre la meteo sur 2 smeaines et la liste des plantes
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}