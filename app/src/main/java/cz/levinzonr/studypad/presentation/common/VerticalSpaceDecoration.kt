package cz.levinzonr.studypad.presentation.common
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.studypad.dp

class VerticalSpaceItemDecoration(private val spaceInDp: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State) {
            outRect.bottom = spaceInDp.dp

        // Leave some space at the bottom to display FAB so that share button is clickable
        parent.adapter?.let {
            if (parent.getChildAdapterPosition(view) == it.itemCount - 1) {
                outRect.bottom = 64.dp
            }
        }

    }
}