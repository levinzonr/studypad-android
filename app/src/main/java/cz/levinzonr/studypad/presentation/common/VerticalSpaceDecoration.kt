package cz.levinzonr.studypad.presentation.common
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.dp

class VerticalSpaceItemDecoration(private val spaceInDp: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State) {

        parent.adapter?.let {
            // Leave some space at the bottom to display FAB so that share button is clickable
            if (parent.getChildAdapterPosition(view) == it.itemCount - 1) {
                outRect.bottom = 64.dp
            }
            outRect.bottom = spaceInDp.dp

            // Leave space for the first in line
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = 4.dp
            }
        }

    }
}