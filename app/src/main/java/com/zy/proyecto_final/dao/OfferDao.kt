package com.zy.proyecto_final.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zy.proyecto_final.pojo.Offer

@Dao
interface OfferDao {

    @Query(value = "SELECT * FROM offer")
    fun getAll(): List<Offer>
    @Query("SELECT * FROM offer WHERE id = :id")
    fun getOfferById(id: Int): Offer?

    @Update
    fun update(offer: Offer)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(offerList: List<Offer>)
    @Transaction
    fun setAll(offerList: List<Offer>) {
        val existingOffers = getAll() // Obtener todos los ofertas existentes
        val offersToAdd = mutableListOf<Offer>()

        for (offer in offerList) {
            val existingOffer = existingOffers.find { it.id == offer.id }
            if (existingOffer == null) {
                // La oferta no existe, agregarla a la lista de ofertas a insertar
                offersToAdd.add(offer)
            } else {
                // La oferta ya existe, verificar si hay cambios en los atributos
                if (existingOffer.title != offer.title || existingOffer.discount != offer.discount || existingOffer.description != offer.description) {
                    // Actualizar los valores de la oferta
                    existingOffer.title = offer.title
                    existingOffer.description = offer.description
                    existingOffer.discount = offer.discount
                    existingOffer.startDate = offer.startDate
                    existingOffer.endDate = offer.endDate
                    update(existingOffer)
                }
            }
        }

        // Insertar los nuevos productos
        if (offersToAdd.isNotEmpty()) {
            insertAll(offersToAdd)
        }
    }


        @Query("DELETE FROM offer")
        fun deleteAll()
    }