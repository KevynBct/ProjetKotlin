package adykb.android.foodplanner

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Meal (var food1 : String = "",
            var food2 : String = "",
            var available1 : Boolean = true,
            var available2 : Boolean = true,
            var evening : Boolean = true,
            var note : String = "",
            var fileName : String = "") : Parcelable, Serializable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        (parcel.readInt() != 0),
        (parcel.readInt() != 0),
        (parcel.readInt() != 0),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(food1)
        parcel.writeString(food2)
        if(available1) parcel.writeInt(1) else parcel.writeInt(0)
        if(available2) parcel.writeInt(1) else parcel.writeInt(0)
        if(evening) parcel.writeInt(1) else parcel.writeInt(0)
        parcel.writeString(note)
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int {
        return 0;
    }

    companion object CREATOR : Parcelable.Creator<Meal>{
        private val serialVersionUid : Long = 9999999999
        override fun createFromParcel(parcel: Parcel): Meal {
            return Meal(parcel)
        }

        override fun newArray(size: Int): Array<Meal?> {
            return arrayOfNulls(size)
        }


    }
}