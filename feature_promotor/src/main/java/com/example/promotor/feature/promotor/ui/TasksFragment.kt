package com.example.promotor.feature.promotor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.promotor.feature.promotor.R
import com.example.promotor.feature.promotor.databinding.FragmentTasksBinding
import com.example.promotor.feature.promotor.databinding.ItemTaskCardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Data class to represent a task
data class Task(
    val id: String, // Unique ID for the task
    val name: String,
    val iconResId: Int, // Resource ID for the task icon (e.g., R.drawable.ic_douyin)
    val progress: String, // e.g., "1/3", "5/10"
    val startTime: Date,
    val endTime: Date
)

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTimeline()
        setupTaskList()
        setupAddButton()

        // Add some dummy tasks for demonstration
        addDummyTasks()
    }

    private fun setupTimeline() {
        val calendarLayout = binding.timelineLayout
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        for (hour in 0..23) {
            val hourTextView = TextView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(R.dimen.timeline_hour_height) // Define this in dimens.xml
                )
                text = sdf.format(Date().apply { hours = hour; minutes = 0 })
                textSize = 12f
                setTextColor(resources.getColor(android.R.color.darker_gray, null))
                setPadding(0, 0, 0, 0)
                gravity = android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL
            }
            calendarLayout.addView(hourTextView)

            if (hour < 23) {
                // Add a divider line between hours
                val divider = View(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        resources.getDimensionPixelSize(R.dimen.timeline_divider_height) // Define this in dimens.xml
                    )
                    setBackgroundColor(resources.getColor(android.R.color.darker_gray, null))
                }
                calendarLayout.addView(divider)
            }
        }
    }

    private fun setupTaskList() {
        taskAdapter = TaskAdapter(tasks)
        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }
    }

    private fun setupAddButton() {
        binding.addTaskButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.task_add_menu, popupMenu.menu) // Create task_add_menu.xml

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_douyin_live_streaming -> showAddTaskDialog("抖音直播带货", R.drawable.ic_douyin_red_square)
                    R.id.menu_xiaohongshu_exploration -> showAddTaskDialog("小红书探店", R.drawable.ic_xiaohongshu_red_square)
                    R.id.menu_wechat_scrm -> showAddTaskDialog("企业微信SCRM", R.drawable.ic_wechat_green_square)
                    R.id.menu_sms_marketing -> showAddTaskDialog("短信营销", R.drawable.ic_sms_orange_square)
                    R.id.menu_email_marketing -> showAddTaskDialog("邮件营销", R.drawable.ic_email_blue_square)
                    R.id.menu_taobao_live_promotion -> showAddTaskDialog("淘宝直播推广", R.drawable.ic_taobao_orange_square)
                    R.id.menu_pinduoduo_store_operation -> showAddTaskDialog("拼多多多店运营", R.drawable.ic_pinduoduo_red_square)
                    R.id.menu_xianyu_product_promotion -> showAddTaskDialog("闲鱼商品推广", R.drawable.ic_xianyu_orange_square)
                    R.id.menu_custom_task -> showAddTaskDialog("自定义任务", R.drawable.ic_custom_task_purple_square) // Placeholder icon
                    else -> false
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun showAddTaskDialog(taskName: String, iconResId: Int) {
        // In a real application, you would have a more complex dialog or a new activity
        // to collect start time, end time, and other task details.
        // For simplicity, let's just add a dummy task for now.

        val startTime = Date() // Placeholder
        val endTime = Date(startTime.time + 60 * 60 * 1000) // Placeholder: 1 hour duration

        val newTask = Task(
            id = System.currentTimeMillis().toString(),
            name = taskName,
            iconResId = iconResId,
            progress = "0/1", // Default progress for newly added task
            startTime = startTime,
            endTime = endTime
        )
        tasks.add(newTask)
        taskAdapter.notifyDataSetChanged()
        binding.taskRecyclerView.scrollToPosition(tasks.size - 1) // Scroll to the new task
    }

    private fun addDummyTasks() {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        val task1StartTime = sdf.parse("10:00")
        val task1EndTime = sdf.parse("12:00")
        if (task1StartTime != null && task1EndTime != null) {
            tasks.add(
                Task(
                    id = "task1",
                    name = "发布抖音作品",
                    iconResId = R.drawable.ic_douyin_red_square,
                    progress = "1/3",
                    startTime = task1StartTime,
                    endTime = task1EndTime
                )
            )
        }

        val task2StartTime = sdf.parse("14:00")
        val task2EndTime = sdf.parse("15:00")
        if (task2StartTime != null && task2EndTime != null) {
            tasks.add(
                Task(
                    id = "task2",
                    name = "发布小红书笔记",
                    iconResId = R.drawable.ic_xiaohongshu_red_square,
                    progress = "0/1",
                    startTime = task2StartTime,
                    endTime = task2EndTime
                )
            )
        }

        val task3StartTime = sdf.parse("18:00")
        val task3EndTime = sdf.parse("20:00")
        if (task3StartTime != null && task3EndTime != null) {
            tasks.add(
                Task(
                    id = "task3",
                    name = "维护微信群",
                    iconResId = R.drawable.ic_wechat_green_square,
                    progress = "5/10",
                    startTime = task3StartTime,
                    endTime = task3EndTime
                )
            )
        }

        val task4StartTime = sdf.parse("22:00")
        val task4EndTime = sdf.parse("23:00")
        if (task4StartTime != null && task4EndTime != null) {
            tasks.add(
                Task(
                    id = "task4",
                    name = "快手直播",
                    iconResId = R.drawable.ic_kuaishou_orange_square,
                    progress = "0/1",
                    startTime = task4StartTime,
                    endTime = task4EndTime
                )
            )
        }

        val task5StartTime = sdf.parse("02:00")
        val task5EndTime = sdf.parse("04:00")
        if (task5StartTime != null && task5EndTime != null) {
            tasks.add(
                Task(
                    id = "task5",
                    name = "回复抖音评论",
                    iconResId = R.drawable.ic_douyin_red_square,
                    progress = "15/50",
                    startTime = task5StartTime,
                    endTime = task5EndTime
                )
            )
        }

        val task6StartTime = sdf.parse("06:00")
        val task6EndTime = sdf.parse("07:00")
        if (task6StartTime != null && task6EndTime != null) {
            tasks.add(
                Task(
                    id = "task6",
                    name = "发布朋友圈",
                    iconResId = R.drawable.ic_wechat_green_square,
                    progress = "0/1",
                    startTime = task6StartTime,
                    endTime = task6EndTime
                )
            )
        }

        taskAdapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // RecyclerView Adapter for tasks
    private inner class TaskAdapter(private val tasks: List<Task>) :
        RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

        inner class TaskViewHolder(val binding: ItemTaskCardBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val binding = ItemTaskCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TaskViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasks[position]
            holder.binding.taskIcon.setImageResource(task.iconResId)
            holder.binding.taskName.text = task.name
            holder.binding.taskProgress.text = task.progress

            // Calculate card position and height based on task start and end times
            val startTimeInMinutes = task.startTime.hours * 60 + task.startTime.minutes
            val endTimeInMinutes = task.endTime.hours * 60 + task.endTime.minutes
            val durationInMinutes = endTimeInMinutes - startTimeInMinutes

            val minutesPerPx = resources.getDimensionPixelSize(R.dimen.timeline_hour_height) / 60f // Assuming 1 hour height is for 60 minutes
            val topMarginPx = (startTimeInMinutes * minutesPerPx).toInt()
            val cardHeightPx = (durationInMinutes * minutesPerPx).toInt()

            val layoutParams = holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = topMarginPx
            layoutParams.height = cardHeightPx
            holder.binding.root.layoutParams = layoutParams

            // Set background color for the task card based on icon (for visual distinction)
            val backgroundColor = when (task.iconResId) {
                R.drawable.ic_douyin_red_square -> R.color.task_card_douyin_bg
                R.drawable.ic_xiaohongshu_red_square -> R.color.task_card_xiaohongshu_bg
                R.drawable.ic_wechat_green_square -> R.color.task_card_wechat_bg
                R.drawable.ic_kuaishou_orange_square -> R.color.task_card_kuaishou_bg
                R.drawable.ic_sms_orange_square -> R.color.task_card_sms_bg
                R.drawable.ic_email_blue_square -> R.color.task_card_email_bg
                R.drawable.ic_taobao_orange_square -> R.color.task_card_taobao_bg
                R.drawable.ic_pinduoduo_red_square -> R.color.task_card_pinduoduo_bg
                R.drawable.ic_xianyu_orange_square -> R.color.task_card_xianyu_bg
                R.drawable.ic_custom_task_purple_square -> R.color.task_card_custom_bg
                else -> android.R.color.white
            }
            holder.binding.taskCardContainer.setCardBackgroundColor(resources.getColor(backgroundColor, null))

            // Adjust text color based on background for better contrast if needed
            val textColor = when (task.iconResId) {
                R.drawable.ic_douyin_red_square, R.drawable.ic_xiaohongshu_red_square, R.drawable.ic_pinduoduo_red_square -> android.R.color.white
                else -> android.R.color.black
            }
            holder.binding.taskName.setTextColor(resources.getColor(textColor, null))
            holder.binding.taskProgress.setTextColor(resources.getColor(textColor, null))
        }

        override fun getItemCount(): Int = tasks.size
    }
}