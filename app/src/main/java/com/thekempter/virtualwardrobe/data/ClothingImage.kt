package com.thekempter.virtualwardrobe.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

@Entity(tableName = "images")
data class ClothingImage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bitData: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClothingImage

        if (id != other.id) return false
        if (!bitData.contentEquals(other.bitData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + bitData.contentHashCode()
        return result
    }
}

class ClothingImageTypeConverter{
    @TypeConverter
    fun fromByteArray(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun toByteArray(bitmap: Bitmap, compressionRatio: Float): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, (compressionRatio * 100).toInt(), outputStream)
        return outputStream.toByteArray()
    }
}
