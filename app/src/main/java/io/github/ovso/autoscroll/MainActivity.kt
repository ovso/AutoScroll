package io.github.ovso.autoscroll

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import io.github.ovso.autoscroll.databinding.ActivityMainBinding
import io.github.ovso.autoscroll.databinding.ItemMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = LogoAdapter().apply {
            submitList(getItems())
        }
        with(binding.rvMain) {
            this.adapter = adapter
            addItemDecoration(LogoItemOffsetDecoration(20))
            val rvAnimator = this.itemAnimator
            (rvAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        setupFlowAnimation(binding.rvMain)
    }

    private fun setupFlowAnimation(rv: RecyclerView) {
        val duration = 60L
        val pixelsToMove = 10
        val handler = Handler(Looper.getMainLooper())
        val scrollingRunnable = object : Runnable {
            override fun run() {
                rv.smoothScrollBy(pixelsToMove, 0)
                handler.postDelayed(this, duration)
            }
        }
        handler.postDelayed(scrollingRunnable, 0)
    }

    fun getItems(): List<LogoModel> {
        return listOf(
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
            LogoModel(id = R.mipmap.ic_launcher_round),
        )
    }
}

class LogoItemOffsetDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.left = offset
        }
    }
}

class MyDiffUtilItemCallBack<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}

data class LogoModel(val id: Int = R.drawable.ic_launcher_foreground) {}

class LogoViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBindViewHolder(item: LogoModel) {
        binding.ivMainItem.setImageResource(item.id)
    }

    companion object {
        fun create(parent: ViewGroup): LogoViewHolder {
            return LogoViewHolder(
                ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}


class LogoAdapter : ListAdapter<LogoModel, LogoViewHolder>(MyDiffUtilItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogoViewHolder {
        return LogoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LogoViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }

    override fun getItemCount(): Int {
        return if (currentList.isEmpty()) 0 else Int.MAX_VALUE
    }

    override fun getItem(position: Int): LogoModel {
        return currentList[position % currentList.size]
    }
}


class ScrollDisabledRv : RecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }
}