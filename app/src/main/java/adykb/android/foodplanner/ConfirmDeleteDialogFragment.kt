package adykb.android.foodplanner

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log

class ConfirmDeleteDialogFragment : android.support.v4.app.DialogFragment() {

    interface ConfirmDeleteListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    val TAG = ConfirmDeleteDialogFragment::class.java.simpleName

    var listener: ConfirmDeleteListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context)
        builder.setMessage("Voulez vous supprimer ce repas ?")
            .setPositiveButton("Oui", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    listener?.onDialogPositiveClick()
                }
            })
            .setNegativeButton("Non", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
                listener?.onDialogNegativeClick()
            })

        return builder.create()
    }
}