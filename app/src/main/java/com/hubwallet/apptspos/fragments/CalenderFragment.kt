package com.hubwallet.apptspos.fragments

import android.annotation.SuppressLint
import android.app.*
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.graphics.RectF
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.error.VolleyError
import com.android.volley.request.StringRequest
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.hubwallet.apptspos.APis.*
import com.hubwallet.apptspos.Activities.GroupAppointmentActivity
import com.hubwallet.apptspos.Activities.NavigationActivity
import com.hubwallet.apptspos.Adapters.PersonalTaskRecyclerViewAdapter
import com.hubwallet.apptspos.Adapters.PersonalTaskTypeAdapter
import com.hubwallet.apptspos.Adapters.ServicesStylistAdapter
import com.hubwallet.apptspos.Adapters.StylsitDialogAdapter
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.Utils.*
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryData
import com.hubwallet.apptspos.Utils.Models.CountryModel.CountryList
import com.hubwallet.apptspos.Utils.Models.EventModel.EventData
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomer
import com.hubwallet.apptspos.Utils.Models.GetCustomerModel.GetCustomerData
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesData
import com.hubwallet.apptspos.Utils.Models.GetServicesModel.GetServicesList
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateData
import com.hubwallet.apptspos.Utils.Models.GetStateModel.GetStateList
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistData
import com.hubwallet.apptspos.Utils.Models.GetStylistModel.GetStylistList
import com.hubwallet.apptspos.Utils.Models.PersonalTaskTypeModel.PersonalTaskTypeData
import com.hubwallet.apptspos.Utils.Models.PersonalTaskTypeModel.PersonalTaskTypeList
import com.hubwallet.apptspos.Utils.Models.ServiceStylist
import com.hubwallet.apptspos.calander.CalendraViewModel
import com.hubwallet.apptspos.calander.Result
import com.hubwallet.apptspos.calander.add_deposit.AddDepositDialog
import com.hubwallet.apptspos.calander.book_multiple_appointment.BookMultipleAppointment
import com.hubwallet.apptspos.calander.filter.FilterAdapter
import com.hubwallet.apptspos.calander.filter.SearchDeatails
import com.hubwallet.apptspos.calander.filter.SearchListener
import com.hubwallet.apptspos.demo.ui.demo.showToast
import com.hubwallet.apptspos.view.week_view.WeekView
import kotlinx.android.synthetic.main.filter_dialog.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalenderFragment : Fragment(), View.OnClickListener, ApiCommunicator, FragmentsComunicator, SearchListener {
    var apiCommunicator: ApiCommunicator? = null
    var communicator: Communicator? = null
    var show = false
    var customerAdapter: ArrayAdapter<String>? = null
    var stylistAdapter: ArrayAdapter<String>? = null
    var serviceAdapter: ArrayAdapter<String>? = null
    private var parent: View? = null
    private val newEvents = ArrayList<Result>()
    private var currentTimeSelected //time selected while clicking on calendar
            : Calendar? = null
    private var eventSelected //time selected while clicking on calendar
            : Result? = null

    //    private int Duration;
    //    private AlertDialog alertDialo;
    private var alertDialo: Dialog? = null
    private var updateCustomers = false
    private var customerList: ArrayList<GetCustomerData>? = ArrayList()
    private var createCustomerDialog: Dialog? = null
    private var customer: AutoCompleteTextView? = null
    private var currentApointmemtId = 0L

    /* private val YEAR = 0
     private val MONTH = 0
     private val DATE = 0
     private val HOUR = 0
     private val MINUTE = 0*/
    private var emptyCliclDialog: android.app.AlertDialog? = null
    private var popupDialogList: FragmentDialogForEventClick? = null
    private var vendorId: String? = null
    private var noteDialog: android.app.AlertDialog? = null
    private var noteEditText: EditText? = null
    private var repeatAppointment: String? = null
    private var endDate: String? = null
    private var stylistList: ArrayList<GetStylistData>? = ArrayList()
    private var servicList: ArrayList<GetServicesData>? = ArrayList()
    private var stylistNamePersonalTask: TextView? = null
    private var stylistNamePersonal: String? = null
    private var stylistIdPersonal: String? = null
    private var personalTaskStylist: AlertDialog? = null
    private var personTaskTypeList: List<PersonalTaskTypeData>? = null
    private var personalTaskID: String? = null
    private var personalTaskName = ""
    private var personalTaskTypeTextView: TextView? = null
    private var dayOff = false
    private var blockOnlineBooling = false
    private var startTimePersonalTask: String? = null
    private var endTimePersonalTask: String? = null
    private var datePersonalTask: String? = null
    private var commentPersonalTask = ""
    private var personalTask: android.app.AlertDialog? = null
    private var floatingActionButton: FloatingActionButton? = null
    private var isVisiblety = false
    private var weekButton: Button? = null
    private var dayButton: Button? = null
    private var filterButton: ImageButton? = null
    private var isForStylistDialog = false
    private var appointmentEventList: ArrayList<Result> = ArrayList()
    private var firstTime = true
    private var formater: SimpleDateFormat? = null
    private var timeFormatter: SimpleDateFormat? = null
    private val groupAppointments: MutableList<Result> = ArrayList()
    private var customProgress: CustomProgress? = null
    private var mActivity: Activity? = null
    private var countryDataList: List<CountryData>? = null
    private var stateList: List<GetStateData>? = null
    private var isFirst = true

    //    private val weekView: WeekView<Result> by lazyView(R.id.weekView)
    lateinit var weekView: WeekView<Result>
    override fun onResume() {
        super.onResume()
        //        newEvents = new ArrayList<>();
        MySingleton.getInstance(mActivity)
                .addToRequestQue(ShowAppointment(PrefManager(mActivity).vendorId, apiCommunicator)
                        .stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        communicator = activity as Communicator?
        apiCommunicator = this
        mActivity = activity
        customProgress = CustomProgress(mActivity!!)
        vendorId = PrefManager(mActivity).vendorId
        FCommunicator = this@CalenderFragment
        MySingleton.getInstance(mActivity)
                .addToRequestQue(GetCustomers(apiCommunicator, vendorId)
                        .stringRequest)
        MySingleton.getInstance(mActivity)
                .addToRequestQue(GetPersonalTaskType(apiCommunicator)
                        .stringRequest)
        MySingleton.getInstance(mActivity)
                .addToRequestQue(GetStylistDataList(apiCommunicator, vendorId)
                        .stringRequest)
        MySingleton.getInstance(mActivity)
                .addToRequestQue(GetServices(apiCommunicator, vendorId)
                        .stringRequest)
    }


    @SuppressLint("SimpleDateFormat", "InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.calendar_fragment, null, false)
        parent = v
        floatingActionButton = v.findViewById(R.id.floatingActionButton)
        weekButton = v.findViewById(R.id.floatingActionButtonWeek)
        dayButton = v.findViewById(R.id.floatingActionButtonDay)
        filterButton = v.findViewById(R.id.filterButton)
        val eventHelper = EventHelper()
        val calendar = eventHelper.getStartDate()
        val calendarEnd = eventHelper.getEndDate()
        //        List<WeekViewDisplayable<EventModel>> data=new ArrayList();
//weekView.submit(data);
        formater = SimpleDateFormat("yyyy-MM-dd")
        timeFormatter = SimpleDateFormat("hh:mm a")
        // Lets change some dimensions to best fit the vie
//        weekView.setEventTextColor(Color.WHITE);

        // Set long press listener for events.
//        weekView.setEventLongPressListener(this);
//        weekView.setEmptyViewClickListener(this);
//        // Set long press listener for empty view
//        weekView.setOnEmptyViewLongClickListener(this);
        weekView = v.findViewById(R.id.weekView)
        weekButton!!.setOnClickListener(View.OnClickListener {
            setButtonHidden()
            weekView.numberOfVisibleDays = 7
            weekView.goToHour(9);
        })
        dayButton!!.setOnClickListener(View.OnClickListener {
            setButtonHidden()
            weekView.numberOfVisibleDays = 1
            weekView.goToHour(9)
        })
        filterButton!!.setOnClickListener(View.OnClickListener { view: View? ->
            setButtonHidden()
//            isForStylistDialog = true
////            MySingleton.getInstance(mActivity)
////                    .addToRequestQue(GetStylists(apiCommunicator, vendorId)
////                            .stringRequest)
            filterShow()
        })
        floatingActionButton!!.setOnClickListener(View.OnClickListener {
            if (!isVisiblety) {
                setButtonShow()
            } else {
                setButtonHidden()
            }
        })
        return v
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekView.setOnEventClickListener { data, rect ->

            eventSelected = data
            if (data.isCheckOut == 1) {
                Toast.makeText(requireContext(), "Appointment already checked out ! ", Toast.LENGTH_SHORT).show()
            } else if (data.id == -1L) {
                Toast.makeText(requireContext(), "Appointments are close at this time!", Toast.LENGTH_SHORT).show()

            } else {
                if (data.appointmentType == 4) {
//                    BookMultipleAppointment(data.date).show(requireActivity().supportFragmentManager, "")
                    currentTimeSelected = Calendar.getInstance()
                    val formater = SimpleDateFormat("yyyy-MM-dd")
                    val timeFormatter = SimpleDateFormat("hh:mm a")
                    val intent = Intent(mActivity, GroupAppointmentActivity::class.java)
                    intent.putExtra("date", formater.format(currentTimeSelected!!.time))
                    intent.putExtra("time", timeFormatter.format(currentTimeSelected!!.time))
                    intent.putExtra("id", data.id)
                    startActivity(intent)
                    return@setOnEventClickListener
                }
                val bundle = Bundle()
                bundle.putString("custumerId", data.customerId)
                bundle.putString("token", data.tokenNo.toString())
                bundle.putString("type", data.appointmentType.toString())
                bundle.putString("appimentId", data.id.toString())
//                val eventClickFragments = EventClickDialog.newInstance();
//                eventClickFragments.arguments = bundle
//                eventClickFragments.show(NavigationActivity.fm, "")
                val e_name = data.title

                bundle.putString("name", e_name)
                popupDialogList = FragmentDialogForEventClick()
                popupDialogList!!.setArguments(bundle)
                popupDialogList!!.setTargetFragment(this, 0)
                assert(fragmentManager != null)
                popupDialogList!!.show(fragmentManager!!, "dialog")

            }


        }
        weekView.setOnEmptyViewClickListener {
            val calendar1 = Calendar.getInstance()
            if (calendar1.timeInMillis < it.getTimeInMillis()) {
                createAlert(it)
            } else {
                Toast.makeText(requireContext(), "Cant do appointment on past dates.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setButtonHidden() {
        filterButton!!.visibility = View.GONE
        weekButton!!.visibility = View.GONE
        dayButton!!.visibility = View.GONE
        isVisiblety = !isVisiblety
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MySingleton.getInstance(mActivity)
                .addToRequestQue(ShowAppointment(PrefManager(mActivity).vendorId, apiCommunicator)
                        .stringRequest)
    }

    private fun setButtonShow() {
        filterButton!!.visibility = View.VISIBLE
        weekButton!!.visibility = View.VISIBLE
        dayButton!!.visibility = View.VISIBLE
        isVisiblety = !isVisiblety
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        NavigationActivity.enableMonthToggle = true
    }

    override fun onDetach() {
        super.onDetach()
        NavigationActivity.enableMonthToggle = false
    }

    @SuppressLint("SimpleDateFormat")
    private fun createAlert(calendar: Calendar) {
        emptyCliclDialog = android.app.AlertDialog.Builder(mActivity, R.style.customDialog).create()
        val v = LayoutInflater.from(mActivity).inflate(R.layout.calender_on_empty_click_dialog, null, false)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams = lp
        emptyCliclDialog!!.setView(v)
        val dateTextView = v.findViewById<TextView>(R.id.dateTextViewPopUp)
        val timeTextView = v.findViewById<TextView>(R.id.timeTextviewPopup)
        val newAppointment = v.findViewById<LinearLayout>(R.id.newAppointmentLinearLayout)
        val groupAppointment = v.findViewById<LinearLayout>(R.id.groupAppointmentLinearLayout)
        val depositAppointment = v.findViewById<LinearLayout>(R.id.addDepositLinearLayout)
        val adToWishList = v.findViewById<LinearLayout>(R.id.addToWaitListLinearLayout)
        val personalTask = v.findViewById<LinearLayout>(R.id.personalTaskLinearLayout)
        val editWorkingHour = v.findViewById<LinearLayout>(R.id.editWorkingHourLinearLayout)
        newAppointment.setOnClickListener(this)
        groupAppointment.setOnClickListener(this)
        depositAppointment.setOnClickListener(this)
        //        adToWishList.setOnClickListener(this);
//        personalTask.setOnClickListener(this);
//        editWorkingHour.setOnClickListener(this);
        currentTimeSelected = calendar
        val formater = SimpleDateFormat("yyyy-MM-dd")
        val timeFormatter = SimpleDateFormat("hh:mm a")
        dateTextView.text = formater.format(calendar.time)
        timeTextView.text = timeFormatter.format(calendar.time)
        //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val wmlp = emptyCliclDialog!!.getWindow()!!.attributes
        wmlp.gravity = Gravity.BOTTOM
        wmlp.windowAnimations = R.style.DialogAnimation
        emptyCliclDialog!!.show()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.groupAppointmentLinearLayout) {
            emptyCliclDialog!!.dismiss()

            val intent = Intent(mActivity, GroupAppointmentActivity::class.java)
            intent.putExtra("date", formater!!.format(currentTimeSelected!!.time))
            intent.putExtra("time", timeFormatter!!.format(currentTimeSelected!!.time))
            intent.putExtra("id", "")
            intent.putExtra("notedit", "notedit")

            startActivity(intent)
        }
        if (id == R.id.newAppointmentLinearLayout) {
            emptyCliclDialog!!.dismiss()
            showAddAppointmentAlert()
        }
        if (id == R.id.addDepositLinearLayout) {
            emptyCliclDialog!!.dismiss()
            AddDepositDialog.getInstance().show(NavigationActivity.fm, "")
        }
        if (id == R.id.personalTaskLinearLayout) {
            val view = LayoutInflater.from(mActivity).inflate(R.layout.personal_task_layout, null, false)
            emptyCliclDialog!!.dismiss()
            personalTask = android.app.AlertDialog.Builder(mActivity).create()
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            v.layoutParams = lp
            personalTask!!.setView(view)
            val wmlp = personalTask!!.getWindow()!!.attributes
            wmlp.gravity = Gravity.CENTER
            wmlp.windowAnimations = R.style.DialogAnimation
            val imageButton = view.findViewById<ImageButton>(R.id.imageButtonPersonalTalk)
            personalTaskTypeTextView = view.findViewById(R.id.personalTaskType)
            val date = view.findViewById<TextView>(R.id.datePersonalTask)
            val addStylistButton = view.findViewById<Button>(R.id.addStylistButtonPersonalTask)
            val blockOnlineBooking = view.findViewById<CheckBox>(R.id.blockOnlineBooking)
            val comment = view.findViewById<EditText>(R.id.commentPersonalTask)
            val submit = view.findViewById<Button>(R.id.submitPersonalTask)
            submit.setOnClickListener { updateTask() }
            comment.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    commentPersonalTask = charSequence.toString()
                }

                override fun afterTextChanged(editable: Editable) {}
            })
            blockOnlineBooking.setOnCheckedChangeListener { compoundButton, b -> blockOnlineBooling = b }
            stylistNamePersonalTask = view.findViewById(R.id.stylistNamePersonalTask)
            addStylistButton.setOnClickListener { MySingleton.getInstance(mActivity).addToRequestQue(GetStylists(apiCommunicator, vendorId).stringRequest) }
            val timeOff = view.findViewById<TextView>(R.id.timeOffPersonalTask)
            timeOff.setOnClickListener {
                val v = layoutInflater.inflate(R.layout.time_off_option_personal_task, null, false)
                val timeOffDialog = android.app.AlertDialog.Builder(mActivity).create()
                timeOffDialog.setView(v)
                val checkBox = v.findViewById<CheckBox>(R.id.checkboxDayOff)
                val startTime = v.findViewById<TextView>(R.id.startTimeTextView)
                val endTime = v.findViewById<TextView>(R.id.endTimeTextView)
                val submit = v.findViewById<Button>(R.id.ButtonPersonalTask)
                val back = v.findViewById<ImageButton>(R.id.backButtonImageTImeOff)
                back.setOnClickListener {
                    timeOffDialog.dismiss()
                    startTimePersonalTask = ""
                    endTimePersonalTask = ""
                    dayOff = false
                }
                submit.setOnClickListener {
                    if (!dayOff) {
                        if (startTimePersonalTask == null || startTimePersonalTask!!.isEmpty()) {
                            Toast.makeText(mActivity, "Select Start Time", Toast.LENGTH_SHORT).show()
                        } else if (endTimePersonalTask == null || endTimePersonalTask!!.isEmpty()) {
                            Toast.makeText(mActivity, "Select End Time", Toast.LENGTH_SHORT).show()
                        } else {
                            timeOff.text = "$startTimePersonalTask-$endTimePersonalTask"
                        }
                    }
                    timeOffDialog.dismiss()
                }
                endTime.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    TimePickerDialog(mActivity, OnTimeSetListener { timePicker, i, i1 ->
                        val calendar_time = Calendar.getInstance()
                        calendar_time[Calendar.MINUTE] = i1
                        calendar_time[Calendar.HOUR] = i
                        val myFormat = "hh:mm a" // your own format
                        val sdf = SimpleDateFormat(myFormat, Locale.US)
                        val formated_time = sdf.format(calendar_time.time)
                        endTime.text = formated_time
                        endTimePersonalTask = formated_time
                    }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true).show()
                }
                startTime.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    TimePickerDialog(mActivity, OnTimeSetListener { timePicker, i, i1 ->
                        val calendar_time = Calendar.getInstance()
                        calendar_time[Calendar.MINUTE] = i1
                        calendar_time[Calendar.HOUR] = i
                        val myFormat = "hh:mm a" // your own format
                        val sdf = SimpleDateFormat(myFormat, Locale.US)
                        val formated_time = sdf.format(calendar_time.time)
                        startTime.text = formated_time
                        startTimePersonalTask = formated_time
                    }, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true).show()
                }
                checkBox.setOnCheckedChangeListener { compoundButton, b ->
                    dayOff = b
                    LogUtils.printLog("onCheckedChanged: ", dayOff.toString() + "")
                }
                timeOffDialog.show()
            }
            date.setOnClickListener {
                val calendar = Calendar.getInstance()
                DatePickerDialog(mActivity!!, OnDateSetListener { datePicker, i, i1, i2 ->
                    date.text = "$i/$i1/$i2"
                    datePersonalTask = "$i/$i1/$i2"
                }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]).show()
            }
            personalTaskTypeTextView!!.setOnClickListener(View.OnClickListener { showTaskType() })
            imageButton.setOnClickListener { personalTask!!.dismiss() }
            personalTask!!.show()
        }
        if (id == R.id.editWorkingHourLinearLayout) {
            val view = LayoutInflater.from(mActivity).inflate(R.layout.working_layout, null, false)
            emptyCliclDialog!!.dismiss()
            val editWorkingHourDialog = android.app.AlertDialog.Builder(mActivity).create()
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            v.layoutParams = lp
            editWorkingHourDialog.setView(view)
            val wmlp = editWorkingHourDialog.window!!.attributes
            wmlp.gravity = Gravity.CENTER
            wmlp.windowAnimations = R.style.DialogAnimation
            val imageButton = view.findViewById<ImageButton>(R.id.imageButtonWorkingHour)
            imageButton.setOnClickListener { editWorkingHourDialog.dismiss() }
            editWorkingHourDialog.show()
        }
        if (id == R.id.addToWaitListLinearLayout) {
            val view = LayoutInflater.from(mActivity).inflate(R.layout.add_to_waitlist, null, false)
            emptyCliclDialog!!.dismiss()
            val addToWishList = android.app.AlertDialog.Builder(mActivity).create()
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            v.layoutParams = lp
            addToWishList.setView(view)
            val wmlp = addToWishList.window!!.attributes
            wmlp.gravity = Gravity.CENTER
            wmlp.windowAnimations = R.style.DialogAnimation
            val closeButton = view.findViewById<ImageButton>(R.id.imageButtonAddtoWaitlist)
            closeButton.setOnClickListener {
                addToWishList.dismiss()
                addToWishList.dismiss()
            }
            addToWishList.show()
        }
    }

    private fun updateTask() {
        val block = if (blockOnlineBooling) "1" else "0"
        val isDayOff = if (dayOff) "0" else ""
        if (stylistIdPersonal == null || stylistIdPersonal.equals("", ignoreCase = true)) {
            Toast.makeText(mActivity, "Select Stylist ", Toast.LENGTH_SHORT).show()
            return
        }
        if (personalTaskID == null || personalTaskID.equals("", ignoreCase = true)) {
            Toast.makeText(mActivity, "Select Task", Toast.LENGTH_SHORT).show()
            return
        }
        if (datePersonalTask == null || datePersonalTask.equals("", ignoreCase = true)) {
            Toast.makeText(mActivity, "Select Task", Toast.LENGTH_SHORT).show()
            return
        }
        MySingleton.getInstance(mActivity).addToRequestQue(SetPersonalTask(apiCommunicator, stylistIdPersonal, personalTaskID, startTimePersonalTask, endTimePersonalTask, datePersonalTask, block, commentPersonalTask, isDayOff).stringRequest)
    }

    @SuppressLint("SimpleDateFormat", "InflateParams")
    private fun showAddAppointmentAlert() {
        val time = currentTimeSelected
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val timeFormat = SimpleDateFormat("HH:mm")
        MySingleton.getInstance(mActivity).addToRequestQue(GetCustomers(apiCommunicator, vendorId).stringRequest)
        updateCustomers = true
        val view = layoutInflater.inflate(R.layout.calendar_fragment123, null)

//        alertDialo = new AlertDialog.Builder(mActivity).create();
//        alertDialo.setView(view);
        alertDialo = Dialog(mActivity!!)
        alertDialo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialo!!.setContentView(view)
        alertDialo!!.setCancelable(false)
        customer = view.findViewById(R.id.appView)
        //        final EditText note;
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        //        final Button cancel, Book, Repeat;
        val btnBookAppointment = view.findViewById<Button>(R.id.btnBookAppointment)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val btnAddGroupService = view.findViewById<Button>(R.id.btnAddGroupService)
        //        final RecyclerView recyclerView = view.findViewById(R.id.serviceStylistRecyclerView);
        val spinService = view.findViewById<Spinner>(R.id.spinService)
        val spinStyler = view.findViewById<Spinner>(R.id.spinStyler)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
        val txtStartTime = view.findViewById<TextView>(R.id.txtTime)
        val addButton = view.findViewById<TextView>(R.id.addDialogButton)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)
        val txtDuration = view.findViewById<TextView>(R.id.txtDuration)
        val editCustomerNote = view.findViewById<EditText>(R.id.editCustomerNote)
        val rBtnOff = view.findViewById<RadioButton>(R.id.rBtnOff)
        val rBtnDaily = view.findViewById<RadioButton>(R.id.rBtnDaily)
        val rBtnWeekly = view.findViewById<RadioButton>(R.id.rBtnWeekly)
        val rBtnMonthly = view.findViewById<RadioButton>(R.id.rBtnMonthly)
        val rBtnYearly = view.findViewById<RadioButton>(R.id.rBtnYearly)
        val customerLayout = view.findViewById<LinearLayout>(R.id.customerLayout)
        val imgCustomerProfile = view.findViewById<ImageView>(R.id.imgCustomerProfile)
        val txtCustomerName = view.findViewById<TextView>(R.id.txtCustomerName)
        val txtCustomerMobile = view.findViewById<TextView>(R.id.txtCustomerMobile)
        val txtCustomerEmail = view.findViewById<TextView>(R.id.txtCustomerEmail)
        val btnAddService = view.findViewById<TextView>(R.id.btnAddService)
        customerLayout.visibility = View.INVISIBLE
        val servicesStylistAdapter = ServicesStylistAdapter(mActivity, null)
        btnAddService.setOnClickListener {
            alertDialo!!.dismiss()
            BookMultipleAppointment(txtDate.text.toString(),"").show(requireActivity().supportFragmentManager, "")
        }
//        Repeat = view.findViewById(R.id.repeatButton);
        addButton.setOnClickListener { v: View? -> addNewCustomerDialog() }

//        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setAdapter(servicesStylistAdapter);
        txtDate.text = dateFormat.format(time!!.time)
        txtStartTime.text = timeFormat.format(time.time)
        txtDate.setOnClickListener { v: View? ->
            DatePickerDialog(mActivity!!, OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                time[Calendar.DAY_OF_MONTH] = dayOfMonth
                time[Calendar.MONTH] = month
                time[Calendar.YEAR] = year
                txtDate.text = dateFormat.format(time.time)
            }, time[Calendar.YEAR], time[Calendar.MONTH], time[Calendar.DAY_OF_MONTH]).show()
        }
        txtStartTime.setOnClickListener { v: View? ->
            TimePickerDialog(mActivity, OnTimeSetListener { view12: TimePicker?, hourOfDay: Int, minute: Int ->
                time[Calendar.HOUR_OF_DAY] = hourOfDay
                time[Calendar.MINUTE] = minute
                txtStartTime.text = timeFormat.format(time.time)
            }, time[Calendar.HOUR_OF_DAY], time[Calendar.MINUTE], true).show()
        }

//        cancel = view.findViewById(R.id.cancelBut);
//        Book = view.findViewById(R.id.bookButton);
//        note = view.findViewById(R.id.custoemrVIew);
        customer!!.setAdapter(customerAdapter)
        spinStyler.adapter = stylistAdapter
        spinService.adapter = serviceAdapter
        spinService.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                txtPrice.text = servicList!![i].price
                txtDuration.text = servicList!![i].duration
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        customer!!.setOnItemClickListener(OnItemClickListener { adapterView, view, position, l ->
            val selection = adapterView.getItemAtPosition(position) as String
            var pos = -1
            for (i in customerList!!.indices) {
                if (customerList!![i].customerName.split("\n").toTypedArray()[0] == selection) {
                    pos = i
                    break
                }
            }
            customerLayout.visibility = View.VISIBLE
            txtCustomerName.text = customerList!![pos].customerName.split("\n").toTypedArray()[0]
            txtCustomerEmail.text = customerList!![pos].email
            txtCustomerMobile.text = customerList!![pos].mobilePhone
            Glide.with(mActivity!!)
                    .load(customerList!![pos].photo)
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(imgCustomerProfile)
            //                Toast.makeText(mActivity, "Position : " + pos, Toast.LENGTH_LONG).show();
        })
        //        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialo.dismiss();
//            }
//        });
        imgClose.setOnClickListener { v: View? -> alertDialo!!.dismiss() }
        btnAddGroupService.setOnClickListener { view13: View? ->
            alertDialo!!.dismiss()
            val intent = Intent(mActivity, GroupAppointmentActivity::class.java)
            intent.putExtra("date", formater!!.format(currentTimeSelected!!.time))
            intent.putExtra("time", timeFormatter!!.format(currentTimeSelected!!.time))
            intent.putExtra("id", "")
            startActivity(intent)
        }
        btnCancel.setOnClickListener { v: View? -> alertDialo!!.dismiss() }

//        Repeat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                repeatAppointment();
//            }
//        });
        btnBookAppointment.setOnClickListener { v: View? ->

//            String servSty = servicesStylistAdapter.getServiceStylistDetail();
//            if (servSty.isEmpty()) {
//                return;
//            }
            if (!customer!!.getText().toString().isEmpty() && customerLayout.visibility == View.VISIBLE) {
//                String date = time.get(Calendar.YEAR) + "-" + (time.get(Calendar.MONTH) + 1) + "-" + time.get(Calendar.DATE);
//                String appointment_time = time.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE) : time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE);
                var customer_id: String? = null
                for (i in customerList!!.indices) {
                    if (customer!!.getText().toString().equals(customerList!![i].customerName.split("\n").toTypedArray()[0], ignoreCase = true)) {
                        customer_id = customerList!![i].customerId
                    }
                }
                customProgress!!.show()
                time[Calendar.MINUTE] = txtDuration.text.toString().toInt()
                MySingleton.getInstance(mActivity)
                        .addToRequestQue(AddAppointment(PrefManager(mActivity).vendorId,
                                customer_id, servicList!![spinService.selectedItemPosition].serviceID,
                                stylistList!![spinStyler.selectedItemPosition].stylistId,
                                formater!!.format(time.time),
                                txtStartTime.text.toString(),
                                timeFormat.format(time.time),
                                editCustomerNote.text.toString(),
                                txtPrice.text.toString(), txtDuration.text.toString(),
                                apiCommunicator)
                                .stringRequest)
            } else {
                Toast.makeText(mActivity, "Enter customer name", Toast.LENGTH_SHORT).show()
            }
        }
        val layoutParams = WindowManager.LayoutParams()
        val windowAlDl = alertDialo!!.window
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowAlDl!!.attributes = layoutParams
        alertDialo!!.show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun getApiData(status: String, response: String) {
        if (status.equals("personal_task_type_input", ignoreCase = true)) {
            personalTaskStylist!!.dismiss()
            try {
                val jsonObject = JSONObject(response)
                personalTaskID = jsonObject.getString("id")
                personalTaskName = jsonObject.getString("name")
                personalTaskTypeTextView!!.text = personalTaskName
            } catch (e: JSONException) {
                LogUtils.printLog("getApiData: ", e.message)
            }
        }
        if (status.equals("personal_task_status", ignoreCase = true)) {
            personalTask!!.dismiss()
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
        }
        if (status.equals("personal_task_type", ignoreCase = true)) {
            personTaskTypeList = GsonBuilder().create().fromJson(response, PersonalTaskTypeList::class.java).data
        }
        if (status.equals("stylist_selected_personal", ignoreCase = true)) {
            try {
                val jsonObject = JSONObject(response)
                stylistNamePersonal = jsonObject.getString("stylistDialog")
                stylistNamePersonalTask!!.text = stylistNamePersonal
                stylistIdPersonal = jsonObject.getString("stylistId")
                personalTaskStylist!!.dismiss()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        if (status.equals("get_stylists", ignoreCase = true)) {
            stylistList!!.clear()
            val getStylistData = GetStylistData()
            getStylistData.stylistId = "0"
            getStylistData.stylistName = "Select"
            stylistList!!.add(getStylistData)
            stylistList!!.addAll(GsonBuilder().create().fromJson(response, GetStylistList::class.java).result)
            if (!isForStylistDialog) {
                isForStylistDialog = false
                showStlistDialog(stylistList)
            } else {
                showStylist(stylistList)
            }
        }
        if (status.equals("get_stylist_data_list", ignoreCase = true)) {
            stylistList!!.clear()
            val getStylistData = GetStylistData()
            getStylistData.stylistId = "0"
            getStylistData.stylistName = "Select"
            stylistList!!.add(getStylistData)
            stylistList!!.addAll(GsonBuilder().create().fromJson(response, GetStylistList::class.java).result)
            val stylistNames: MutableList<String> = ArrayList()
            for (data in stylistList!!) {
                stylistNames.add(data.stylistName.split("\n").toTypedArray()[0])
            }
            stylistAdapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, stylistNames)
            //            customer.setAdapter(customerAdapter);
        }
        if (status.equals("services_list", ignoreCase = true)) {
            val getServicesData = GetServicesData()
            getServicesData.serviceName = "Select"
            servicList!!.clear()
            servicList!!.add(getServicesData)
            servicList!!.addAll(GsonBuilder().create().fromJson(response, GetServicesList::class.java).result)

            val serviceListName: MutableList<String> = ArrayList()
            for (data in servicList!!) {
                serviceListName.add(data.serviceName.split("\n").toTypedArray()[0])
            }
            serviceAdapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, serviceListName)
        }
        if (status.equals("update_note", ignoreCase = true)) {
            noteDialog!!.dismiss()
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
        }
        if (status.equals("get_notes", ignoreCase = true)) {
            noteEditText!!.setText(response)
        }
        if (status.equals("customer_added", ignoreCase = true)) {
            createCustomerDialog!!.dismiss()
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
            MySingleton.getInstance(mActivity).addToRequestQue(GetCustomers(apiCommunicator, vendorId).stringRequest)
        }
        if (status.equals("appointment_deleted", ignoreCase = true)) {
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
            //            newEvents = null;
//            newEvents = new ArrayList<>();
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(ShowAppointment(PrefManager(mActivity).vendorId, apiCommunicator)
                            .stringRequest)
        }
        if (status.equals("get_customers", ignoreCase = true)) {
            customerList!!.clear()
            val getCustomerData = GetCustomerData()
            getCustomerData.customerName = "Select"
            customerList!!.add(getCustomerData)
            customerList!!.addAll(GsonBuilder().create().fromJson(response, GetCustomer::class.java).result)
            val customerNames: MutableList<String> = ArrayList()
            for (data in customerList!!) {
                customerNames.add(data.customerName.split("\n").toTypedArray()[0])
            }
            customerAdapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, customerNames)
            if (updateCustomers) {
                customer!!.threshold = 0
                customer!!.setAdapter(customerAdapter)
                customer!!.showDropDown()
                updateCustomers = false
            }
        }
        if (status.equals("detail_appointment", ignoreCase = true)) {
            createEditDetailAlert(response)
        } else if (status.equals("appointment_add_1", ignoreCase = true)) {
            customProgress!!.dismiss()
            alertDialo!!.dismiss()
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
            //            newEvents = null;
//            newEvents = new ArrayList<>();
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(ShowAppointment(PrefManager(mActivity).vendorId, apiCommunicator)
                            .stringRequest)
        } else if (status.equals("appointment_add_0", ignoreCase = true)) {
            customProgress!!.dismiss()
            alertDialo!!.dismiss()
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
        } else if (status.equals("event_list", ignoreCase = true)) {
//            newEvents = new ArrayList<>();
            newEvents.clear()
            appointmentEventList.clear()
            Log.d("event_list", response)
            val gson = GsonBuilder().create()
            appointmentEventList.addAll(gson.fromJson(response, EventData::class.java).message)
            if (gson.fromJson(response, EventData::class.java).depositDetailsList != null) {
                val list = gson.fromJson(response, EventData::class.java).depositDetailsList
                var result: Result? = null
                for (value in list) {
//                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//                    val output = SimpleDateFormat("yyyy-MM-dd HH:mm")
//                    val d = sdf.parse( value.start)
//                    val formattedTime = output.format(d)
                    var dif = CommonUtils.differenceTwoDate(value.start, value.end)
                    Log.d("times", "$dif")
                    if (dif < 1) {
                        dif = 60
                    }
                    result = Result(4, value.color, value.color, "11", value.start, dif, 0, value.token1.toLong(),
                            value.rendering, value.title, value.token1, false, "")
                    appointmentEventList.add(result)
                }

            }
            if (isFirst) {
                isFirst = false
                weekView.submit(appointmentEventList)
            } else {
                weekView.invalidate()
                weekView.notifyDataSetChanged()
                weekView.submit(appointmentEventList)
            }

            for (e in appointmentEventList!!) {
                try {
                    if (e.appointmentType == 3) {
                        groupAppointments.add(e)
                    }

//
                    val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    sdf1.parse(e.date)
                    val startTime = Calendar.getInstance()
                    startTime.time = sdf1.parse(e.date)

//
                    val endTime = Calendar.getInstance()
                    endTime.time = sdf1.parse(e.date)
                    endTime.add(Calendar.MINUTE, e.duration.toInt())
                } catch (ex: Exception) {
                    LogUtils.printLog("Invalid Calendar Date", "::  " + ex.message)
                }
            }
            val activity = mActivity as NavigationActivity?
            activity!!.offProgress()
            if (firstTime) {
                firstTime = false
                weekView.goToToday();
                weekView.goToHour(9);
            }
        }
        if (status.equals("appointment_status", ignoreCase = true)) {
//            newEvents = null;
//            newEvents = new ArrayList<>();
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(ShowAppointment(PrefManager(mActivity).vendorId, apiCommunicator)
                            .stringRequest)
        }
        if (status.equals("appointment_status_checkout", ignoreCase = true)) {
            communicator!!.sendmessage("checkout", currentApointmemtId.toString())
        }
        if (status.equals("edit_appointment", ignoreCase = true)) {
            Toast.makeText(mActivity, response, Toast.LENGTH_SHORT).show()
            //            newEvents = null;
//            newEvents = new ArrayList<>();
            customProgress!!.dismiss()
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(ShowAppointment(PrefManager(mActivity).vendorId, apiCommunicator)
                            .stringRequest)
        }
    }

    private fun showStlistDialog(stylistList: List<GetStylistData>?) {
        val view1 = layoutInflater.inflate(R.layout.stylist_list_dialog, null, false)
        val adapter = StylsitDialogAdapter(mActivity, stylistList)
        val stylistDialog = AlertDialog.Builder(mActivity!!).create()
        stylistDialog.setView(view1)
        val recyclerView: RecyclerView = view1.findViewById(R.id.stylistListDialogRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.adapter = adapter
        val chooseButton = view1.findViewById<Button>(R.id.chooseButton)
        chooseButton.setOnClickListener {
            var selectedStylist = adapter.selectedStylist
            selectedStylist = selectedStylist.replace("[", "")
            selectedStylist = selectedStylist.replace("]", "")
            //                newEvents = null;// for loading new List
//                newEvents = new ArrayList<>();
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(ShowAppointment(vendorId, apiCommunicator, selectedStylist)
                            .stringRequest)
            stylistDialog.dismiss()
        }
        stylistDialog.show()
    }

    private fun showStylist(stylistList: List<GetStylistData>?) {
        personalTaskStylist = AlertDialog.Builder(mActivity!!).create()
        val veiw = layoutInflater.inflate(R.layout.personal_task_stylist, null, false)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        veiw.layoutParams = lp
        val recyclerView: RecyclerView = veiw.findViewById(R.id.stylistListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.adapter = PersonalTaskRecyclerViewAdapter(stylistList, mActivity, apiCommunicator)
        personalTaskStylist!!.setView(veiw)
        personalTaskStylist!!.show()
    }

    private fun showTaskType() {
        personalTaskStylist = null //alert
        personalTaskStylist = AlertDialog.Builder(mActivity!!).create()
        val veiw = layoutInflater.inflate(R.layout.personal_task_stylist, null, false)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        veiw.layoutParams = lp
        val recyclerView: RecyclerView = veiw.findViewById(R.id.stylistListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.adapter = PersonalTaskTypeAdapter(personTaskTypeList, mActivity, apiCommunicator)
        personalTaskStylist!!.setView(veiw)
        personalTaskStylist!!.show()
    }

    @SuppressLint("InflateParams")
    private fun addNewCustomerDialog() {
        val v = LayoutInflater.from(mActivity).inflate(R.layout.add_new_customer, null, false)
        createCustomerDialog = Dialog(mActivity!!)
        createCustomerDialog!!.setContentView(v)
        val firstname: EditText
        val lastName: EditText
        val email: EditText
        val mobile: EditText
        val editCCAppointmentEmail: EditText
        val editHomePhone: EditText
        val editReferredBy: EditText
        val editCity: EditText
        val editWorkPhone: EditText
        val editAddress: EditText
        val spinCountry: Spinner
        val spinState: Spinner
        val spinGender: Spinner
        val spinPreferredContact: Spinner
        val checkSMS: CheckBox
        val checkEmail: CheckBox
        val cancelButton: Button
        val addCustomerButton: Button
        firstname = v.findViewById(R.id.firstNameField)
        lastName = v.findViewById(R.id.lastNameField)
        email = v.findViewById(R.id.emailField)
        mobile = v.findViewById(R.id.editMobileNo)
        editCCAppointmentEmail = v.findViewById(R.id.editCCAppointmentEmail)
        editHomePhone = v.findViewById(R.id.editHomePhone)
        editReferredBy = v.findViewById(R.id.editReferredBy)
        editCity = v.findViewById(R.id.editCity)
        editWorkPhone = v.findViewById(R.id.editWorkPhone)
        editAddress = v.findViewById(R.id.editAddress)
        spinCountry = v.findViewById(R.id.spinCountry)
        spinState = v.findViewById(R.id.spinState)
        spinGender = v.findViewById(R.id.spinGender)
        spinPreferredContact = v.findViewById(R.id.spinPreferredContact)
        checkSMS = v.findViewById(R.id.checkSMS)
        checkEmail = v.findViewById(R.id.checkEmail)
        cancelButton = v.findViewById(R.id.cancelButton)
        addCustomerButton = v.findViewById(R.id.addCustomerBUtton)
        cancelButton.setOnClickListener { v1: View? -> createCustomerDialog!!.dismiss() }
        spinGender.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, genderArr)
        spinPreferredContact.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, preferredContactArr)
        addCustomerButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val fname = firstname.text.toString()
                val lname = lastName.text.toString()
                val ema = email.text.toString()
                val mob = mobile.text.toString()
                if (fname.isEmpty()) {
                    sendError(firstname)
                    return
                } else if (lname.isEmpty()) {
                    sendError(lastName)
                    return
                } else if (ema.isEmpty()) {
                    sendError(email)
                    return
                } else if (!Patterns.EMAIL_ADDRESS.matcher(ema).matches()) {
                    sendValidError(email)
                    return
                } else if (editCCAppointmentEmail.text.toString().length > 0
                        && !Patterns.EMAIL_ADDRESS.matcher(editCCAppointmentEmail.text.toString()).matches()) {
                    sendValidError(editCCAppointmentEmail)
                    return
                } else if (mob.isEmpty()) {
                    sendError(mobile)
                    return
                } else if (mob.length != 10) {
                    sendValidError(mobile)
                    return
                } else if (!checkSMS.isChecked && !checkEmail.isChecked) {
                    sendError(checkSMS)
                    sendError(checkEmail)
                    return
                } else {
//                    MySingleton.getInstance(mActivity)
//                            .addToRequestQue(new AddCustomer(fname, lname, ema, mob, "", "", "", "", "", vendorID, "", apiCommunicator)
//                                    .getStringRequest());
                    customProgress!!.show()
                    MySingleton.getInstance(mActivity)
                            .addToRequestQue(AddCustomerPopup(vendorId, firstname.text.toString(),
                                    lastName.text.toString(), email.text.toString(),
                                    mobile.text.toString(), editHomePhone.text.toString(),
                                    editWorkPhone.text.toString(), editAddress.text.toString(),
                                    editCity.text.toString(),
                                    stateList!![spinState.selectedItemPosition].stateId,
                                    countryDataList!![spinCountry.selectedItemPosition].countryD,
                                    genderArr[spinGender.selectedItemPosition], (spinPreferredContact.selectedItemPosition + 1).toString(),
                                    editCCAppointmentEmail.text.toString(),
                                    editReferredBy.text.toString(),
                                    if (checkSMS.isChecked) "1" else "0",
                                    if (checkSMS.isChecked) "1" else "0",
                                    apiCommunicator).stringRequest)
                }
            }

            private fun sendError(checkBox: CheckBox) {
                checkBox.error = "Field Cant be Empty"
            }

            private fun sendError(mobile: EditText) {
                mobile.error = "Field Cant be Empty"
            }

            private fun sendValidError(mobile: EditText) {
                mobile.error = "Enter valid data."
            }
        })
        val countryRequest = StringRequest(Request.Method.POST,
                Constants.getCountry, label@ Response.Listener { response: String? ->
            var jsonObject: JSONObject? = null
            try {
                jsonObject = JSONObject(response)
                val status = jsonObject.getString("status")
                if (status.equals("1", ignoreCase = true)) {
                    countryDataList = GsonBuilder().create().fromJson(response, CountryList::class.java).result
                    val countryList = ArrayList<String>()
                    if (countryDataList != null) {
                        for (data in countryDataList!!) {
                            countryList.add(data.countryName)
                        }
                    }
                    if (countryList.size > 0) {
                        spinCountry.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, countryList)
                        spinCountry.onItemSelectedListener = object : OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                                val stateRequest: StringRequest = object : StringRequest(Method.POST, Constants.getState, Response.Listener { response ->
                                    var jsonObject: JSONObject? = null
                                    try {
                                        jsonObject = JSONObject(response)
                                        val status = jsonObject!!.getString("status")
                                        if (status.equals("1", ignoreCase = true)) {
                                            stateList = GsonBuilder().create().fromJson(response, GetStateList::class.java).result
                                            val list = ArrayList<String>()
                                            for (d in stateList!!) {
                                                list.add(d.stateName)
                                            }
                                            if (list.size > 0) {
                                                spinState.adapter = ArrayAdapter(mActivity!!, R.layout.li_spin_search, list)
                                            }
                                        }
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }, Response.ErrorListener { error: VolleyError -> LogUtils.printLog("STate Error", "::  " + error.message) }) {
                                    override fun getParams(): Map<String, String> {
                                        val param = HashMap<String, String>()
                                        param["country_id"] = countryDataList!!.get(position).countryD
                                        return param
                                    }
                                }
                                MySingleton.getInstance(mActivity).addToRequestQue(stateRequest)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error: VolleyError -> LogUtils.printLog("Response", error.message) })
        MySingleton.getInstance(mActivity).addToRequestQue(countryRequest)
        val layoutParams = WindowManager.LayoutParams()
        val windowAlDl = createCustomerDialog!!.window
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowAlDl!!.attributes = layoutParams
        createCustomerDialog!!.show()
    }

    fun changeMonth(action: Int) {
        val calendar = weekView.firstVisibleDate

        if (action == 1) {
            calendar[Calendar.WEEK_OF_YEAR] = calendar[Calendar.WEEK_OF_YEAR] + 1
        } else {
            calendar[Calendar.WEEK_OF_YEAR] = calendar[Calendar.WEEK_OF_YEAR] - 1
        }
        weekView.goToDate(calendar)
    }

    @SuppressLint("SimpleDateFormat", "InflateParams")
    private fun createEditDetailAlert(response: String) {
        val mainDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val timeFormat = SimpleDateFormat("HH:mm")
        val time = Calendar.getInstance()
        MySingleton.getInstance(mActivity)
                .addToRequestQue(GetCustomers(apiCommunicator, vendorId)
                        .stringRequest)
        updateCustomers = true
        val view = layoutInflater.inflate(R.layout.calendar_fragment123, null, false)
        val updateSingleApptDialog = Dialog(mActivity!!)
        updateSingleApptDialog.setContentView(view)
        updateSingleApptDialog.setCancelable(false)
        var appointmentID: String? = null
        customer = view.findViewById(R.id.appView)
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        val btnBookAppointment = view.findViewById<Button>(R.id.btnBookAppointment)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val btnAddGroupService = view.findViewById<Button>(R.id.btnAddGroupService)
        val spinService = view.findViewById<Spinner>(R.id.spinService)
        val spinStyler = view.findViewById<Spinner>(R.id.spinStyler)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)
        val txtStartTime = view.findViewById<TextView>(R.id.txtTime)
        val addButton = view.findViewById<TextView>(R.id.addDialogButton)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)
        val txtDuration = view.findViewById<TextView>(R.id.txtDuration)
        val editCustomerNote = view.findViewById<EditText>(R.id.editCustomerNote)
        /*  val rBtnOff = view.findViewById<RadioButton>(R.id.rBtnOff)
          val rBtnDaily = view.findViewById<RadioButton>(R.id.rBtnDaily)
          val rBtnWeekly = view.findViewById<RadioButton>(R.id.rBtnWeekly)
          val rBtnMonthly = view.findViewById<RadioButton>(R.id.rBtnMonthly)
          val rBtnYearly = view.findViewById<RadioButton>(R.id.rBtnYearly)*/
        val customerLayout = view.findViewById<LinearLayout>(R.id.customerLayout)
        val imgCustomerProfile = view.findViewById<ImageView>(R.id.imgCustomerProfile)
        val txtCustomerName = view.findViewById<TextView>(R.id.txtCustomerName)
        val txtCustomerMobile = view.findViewById<TextView>(R.id.txtCustomerMobile)
        val txtCustomerEmail = view.findViewById<TextView>(R.id.txtCustomerEmail)
        addButton.visibility = View.INVISIBLE
        btnAddGroupService.visibility = View.INVISIBLE
        customer!!.setAdapter(customerAdapter)
        spinStyler.adapter = stylistAdapter
        spinService.adapter = serviceAdapter
        spinService.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                txtPrice.text = servicList!![i].price
                txtDuration.text = servicList!![i].duration
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        var dateRecieved: String? = null
        var customerId = ""
        val list = ArrayList<ServiceStylist>()
        try {
            val jsonObject = JSONObject(response)
            val resulrt = JSONArray(jsonObject.getString("result"))
            for (i in 0 until resulrt.length()) {
                val a = ServiceStylist()
                a.stylistName = resulrt.getJSONObject(i).getString("stylist_name")
                a.stylistId = resulrt.getJSONObject(i).getString("stylist_id")
                a.serviceName = resulrt.getJSONObject(i).getString("title")
                a.serviceId = resulrt.getJSONObject(i).getString("service_id")
                list.add(a)
            }
            if (list.size == 1) {
                for (i in servicList!!.indices) {
                    if (resulrt.getJSONObject(0).getString("service_id") == servicList!![i].serviceID) {
                        spinService.setSelection(i)
                        break
                    }
                }
                for (i in stylistList!!.indices) {
                    if (resulrt.getJSONObject(0).getString("stylist_id") == stylistList!![i].stylistId) {
                        spinStyler.setSelection(i)
                        break
                    }
                }
            }
            val re = resulrt.getJSONObject(0)
            val cutomer_id = re.getString("customer_id")
            val notes = re.getString("note")
            dateRecieved = re.getString("date")
            editCustomerNote.setText(notes)
            appointmentID = re.getString("id")
            var customerpos = 0
            for (i in customerList!!.indices) {
                if (cutomer_id.equals(customerList!![i].customerId, ignoreCase = true)) {
                    customerpos = i
                    customerId = customerList!![i].customerId
                    break
                }
            }
            //            customer.setEnabled(false);
            customer!!.setText(customerList!![customerpos].customerName.split("\n").toTypedArray()[0])
            txtCustomerName.text = customerList!![customerpos].customerName.split("\n").toTypedArray()[0]
            txtCustomerEmail.text = customerList!![customerpos].email
            txtCustomerMobile.text = customerList!![customerpos].mobilePhone
            Glide.with(mActivity!!)
                    .load(customerList!![customerpos].photo)
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(imgCustomerProfile)
            val dd1 = mainDateFormat.parse(dateRecieved)
            time.time = dd1
            txtDate.text = dateFormat.format(dd1)
            txtStartTime.text = timeFormat.format(dd1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        txtDate.setOnClickListener { v: View? ->
            DatePickerDialog(mActivity!!, OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                time[Calendar.DAY_OF_MONTH] = dayOfMonth
                time[Calendar.MONTH] = month
                time[Calendar.YEAR] = year
                txtDate.text = dateFormat.format(time.time)
            }, time[Calendar.YEAR], time[Calendar.MONTH], time[Calendar.DAY_OF_MONTH]).show()
        }
        txtStartTime.setOnClickListener { v: View? ->

            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val dialog = TimePickerDialog(mActivity, OnTimeSetListener { view12: TimePicker?, hourOfDay: Int, minute: Int ->
//                time[Calendar.HOUR_OF_DAY] = hourOfDay
//                time[Calendar.MINUTE] = minute
                var am_pm = ""
                val datetime = Calendar.getInstance()
                datetime[Calendar.HOUR_OF_DAY] = hourOfDay
                datetime[Calendar.MINUTE] = minute
                if (datetime[Calendar.AM_PM] == Calendar.AM) am_pm = "AM" else if (datetime[Calendar.AM_PM] == Calendar.PM) am_pm = "PM"
                if ((am_pm == "AM" && hourOfDay < 9)) {
                    showToast("Please select time in 24 hours between 9 to 22")
                    return@OnTimeSetListener
                }
                val strHrsToShow = if (datetime[Calendar.HOUR] == 0) "12" else datetime[Calendar.HOUR].toString() + ""
//                txtStartTime.text = timeFormat.format(time.time)
                txtStartTime.text = "$hourOfDay:$minute"

                Log.d("timeshow", "$strHrsToShow $hourOfDay:$minute $am_pm")
            }, hour, minute, true)

            dialog.show()
        }
        customer!!.setOnItemClickListener(OnItemClickListener { adapterView, view, position, _ ->
            val selection = adapterView.getItemAtPosition(position) as String
            var pos = -1
            for (i in customerList!!.indices) {
                if (customerList!![i].customerName.split("\n").toTypedArray()[0] == selection) {
                    pos = i
                    break
                }
            }
            customerLayout.visibility = View.VISIBLE
            txtCustomerName.text = customerList!![pos].customerName.split("\n").toTypedArray()[0]
            txtCustomerEmail.text = customerList!![pos].email
            txtCustomerMobile.text = customerList!![pos].mobilePhone
            Glide.with(mActivity!!)
                    .load(customerList!![pos].photo)
                    .placeholder(R.drawable.place_holder_1)
                    .error(R.drawable.place_holder_1)
                    .into(imgCustomerProfile)
            //                Toast.makeText(mActivity, "Position : " + pos, Toast.LENGTH_LONG).show();
        })
        btnCancel.setOnClickListener { v: View? -> updateSingleApptDialog.dismiss() }
        imgClose.setOnClickListener { v: View? -> updateSingleApptDialog.dismiss() }
        val finalAppointmentID = appointmentID
        btnBookAppointment.text = "Update"
        val finalCustomerId = customerId
        btnBookAppointment.setOnClickListener {
            if (!customer!!.getText().toString().isEmpty()) {

//                    String date = YEAR + "-" + MONTH + "-" + DATE;
//                    String time = HOUR < 10 ? "0" + HOUR + ":" + MINUTE : HOUR + ":" + MINUTE;
                updateSingleApptDialog.dismiss()
                //      createEvent(title, min, time);
                val endTimeCal = time.clone() as Calendar
                customProgress!!.show()
                endTimeCal.add(Calendar.MINUTE, txtDuration.text.toString().toInt())
                MySingleton.getInstance(mActivity)
                        .addToRequestQue(EditAppointment(finalAppointmentID,
                                PrefManager(mActivity).vendorId,
                                formater!!.format(time.time),
                                servicList!![spinService.selectedItemPosition].serviceID,
                                stylistList!![spinStyler.selectedItemPosition].stylistId,
                                txtDuration.text.toString(),
                                editCustomerNote.text.toString(),
                                finalCustomerId,
                                timeFormat.format(time.time),
                                timeFormat.format(endTimeCal.time),
                                apiCommunicator).stringRequest)
            } else {
                Toast.makeText(mActivity, "Enter customer name", Toast.LENGTH_SHORT).show()
            }
        }
        //        repeat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                repeatAppointment();
//            }
//        });
        val layoutParams = WindowManager.LayoutParams()
        val windowAlDl = updateSingleApptDialog.window
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowAlDl!!.attributes = layoutParams
        updateSingleApptDialog.show()
    }

    override fun message(message: String) {
        popupDialogList!!.dismiss()
        currentApointmemtId = eventSelected!!.id
        if (message.equals("notes", ignoreCase = true)) {
            notes
            noteDialog = android.app.AlertDialog.Builder(mActivity).create()
            val v = layoutInflater.inflate(R.layout.custom_notes_layout, null, false)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            v.layoutParams = lp
            noteDialog!!.setView(v)
            val wmlp = noteDialog!!.getWindow()!!.attributes
            wmlp.gravity = Gravity.CENTER
            wmlp.windowAnimations = R.style.DialogAnimation
            val cancel: Button
            val update: Button
            noteEditText = v.findViewById(R.id.editTextNotes)
            cancel = v.findViewById(R.id.cancelNoteButton)
            update = v.findViewById(R.id.updateNoteButton)
            cancel.setOnClickListener { noteDialog!!.dismiss() }
            update.setOnClickListener { MySingleton.getInstance(mActivity).addToRequestQue(UpdateNotes(PrefManager(mActivity).vendorId, eventSelected!!.id.toString() + "", noteEditText!!.getText().toString(), apiCommunicator).stringRequest) }
            noteDialog!!.show()
        } else if (message.equals("customer_info", ignoreCase = true)) {
            val customerInfoFragment = CustomerInfoFragment()
            customerInfoFragment.show(fragmentManager!!, message)
        } else if (message.equals("delete", ignoreCase = true)) {
            MySingleton.getInstance(mActivity)
                    .addToRequestQue(DeleteAppointment(PrefManager(mActivity).vendorId, currentApointmemtId.toString() + "", apiCommunicator).stringRequest)
        } else if (message.equals("edit", ignoreCase = true)) {
            var isGroupAppointment = false
            var token = ""
            for (ev in groupAppointments) {
                if (currentApointmemtId == ev.id) {
                    token = ev.tokenNo
                    isGroupAppointment = true
                    break
                }
            }
            if (!isGroupAppointment) {
                MySingleton.getInstance(mActivity)
                        .addToRequestQue(ShowDetailAppointment(PrefManager(mActivity).vendorId,
                                currentApointmemtId.toString() + "", apiCommunicator)
                                .stringRequest)
            } else {
                currentTimeSelected = Calendar.getInstance()
                val intent = Intent(mActivity, GroupAppointmentActivity::class.java)
                intent.putExtra("token_number", token)
                intent.putExtra("date", formater!!.format(currentTimeSelected!!.time))
                intent.putExtra("time", timeFormatter!!.format(currentTimeSelected!!.time))
                intent.putExtra("id", "")
                startActivity(intent)
            }
        } else {

            ///     MySingleton.getInstance(mActivity).addToRequestQue(new AppointmentStatus(eventSelected.getId() + "", message, apiCommunicator).getStringRequest());
            communicator!!.sendmessage("checkout", eventSelected!!.id.toString() + "")
        }
    }

    private val notes: Unit
        get() {
            MySingleton.getInstance(mActivity).addToRequestQue(GetNotes(PrefManager(mActivity).vendorId, eventSelected!!.id.toString() + "", apiCommunicator).stringRequest)
        }

    @SuppressLint("InflateParams")
    private fun repeatAppointment() {
        val rebookAlert = android.app.AlertDialog.Builder(mActivity).create()
        val v = layoutInflater.inflate(R.layout.rebook_custom_layout, null, false)
        val linearLayout = v.findViewById<LinearLayout>(R.id.linearLayoutReebook)
        linearLayout.visibility = View.GONE
        val radioGroup = v.findViewById<RadioGroup>(R.id.radioGroupRebook)
        val off = v.findViewById<RadioButton>(R.id.radioButtonOff)
        val month = v.findViewById<RadioButton>(R.id.radioButtonMonthly)
        val week = v.findViewById<RadioButton>(R.id.radioButtonWeekly)
        val yearly = v.findViewById<RadioButton>(R.id.radioButtonYearly)
        val day = v.findViewById<RadioButton>(R.id.radioButtonDaily)
        val offButton = v.findViewById<RadioButton>(R.id.radioButtonOff)
        off.isChecked = true
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            linearLayout.removeAllViews()
            linearLayout.visibility = View.GONE
            repeatAppointment = "0"
            if (i == offButton.id) {
                repeatAppointment = "0"
                endDate = "0"
            }
            if (i == day.id) {
                val v = layoutInflater.inflate(R.layout.daily_rebook_layout, null, false)
                linearLayout.addView(v)
                linearLayout.visibility = View.VISIBLE
            }
            if (i == week.id) {
                val v = layoutInflater.inflate(R.layout.week_reebook_layout, null, false)
                linearLayout.addView(v)
                linearLayout.visibility = View.VISIBLE
            }
            if (i == month.id) {
                val v = layoutInflater.inflate(R.layout.month_custom_view, null, false)
                linearLayout.addView(v)
                linearLayout.visibility = View.VISIBLE
            }
            if (i == yearly.id) {
                val v = layoutInflater.inflate(R.layout.yearly_custom_view, null, false)
                linearLayout.addView(v)
                linearLayout.visibility = View.VISIBLE
            }
        }
        rebookAlert.setView(v)
        rebookAlert.show()
    }

    fun onEventClick(data: Result, eventRect: RectF) {
        eventSelected = data
        for (e in appointmentEventList!!) {
//            if (e.id.equals(data.id.toString() +  && data.id != -1L && e.colorId.equals("8", ignoreCase = true)) {
//                Toast.makeText(mActivity, "Appointment already checked out ! ", Toast.LENGTH_SHORT).show()
//                return
//            }
        }
        if (data.id == -1L) {
            Toast.makeText(mActivity, "Appointments are close at this time!", Toast.LENGTH_SHORT).show()
            return
        }
        val bundle = Bundle()
        val e_name = data.title
        LogUtils.printLog("Selected Booking", "" + e_name)
        val name = e_name.split(", ").toTypedArray()
        val n = name[3].split("-").toTypedArray()
        bundle.putString("name", n[1] + " " + name[4])
        bundle.putString("service", name[0])
        popupDialogList = FragmentDialogForEventClick()
        popupDialogList!!.arguments = bundle
        popupDialogList!!.setTargetFragment(this@CalenderFragment, 0)
        assert(fragmentManager != null)
        popupDialogList!!.show(fragmentManager!!, "dialog")
    }

    lateinit var searchDialog: Dialog
    lateinit var calendraViewModel: CalendraViewModel
    val list = ArrayList<SearchDeatails>()
    lateinit var filterAdapter: FilterAdapter

    @SuppressLint("InflateParams")
    private fun filterShow() {

        if (!::searchDialog.isInitialized) {
            calendraViewModel = ViewModelProvider(this).get(CalendraViewModel::class.java)
            val view = layoutInflater.inflate(R.layout.filter_dialog, null)
            searchDialog = Dialog(mActivity!!)
            searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            searchDialog.setContentView(view)
            searchDialog.filterRV.layoutManager = LinearLayoutManager(requireContext())
            filterAdapter = FilterAdapter(requireContext(), list, this)
            searchDialog.filterRV.adapter = filterAdapter
            searchDialog.searchBtn.setOnClickListener {
                calendraViewModel.searchAppointment(PrefManager(requireContext()).vendorId, searchDialog.edtSearch.text.toString())
                calendraViewModel.liveData!!.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    list.clear()
                    if (it.status == 1) {
                        list.addAll(it.result)
                    }
                    filterAdapter.notifyDataSetChanged()
                })

            }

        }

        val layoutParams = WindowManager.LayoutParams()
        val windowManager: WindowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = windowManager.defaultDisplay.width
        val windowAlDl = searchDialog.window
        layoutParams.width = (width * .70).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowAlDl!!.attributes = layoutParams
        searchDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onSearchClick(position: Int) {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        cal.time = df.parse(list[position].appointment_date)
        weekView.goToDate(cal)
        searchDialog.dismiss()
    }

    companion object {
        private val genderArr = arrayOf("Male", "Female")
        private val preferredContactArr = arrayOf("Mobile", "Home", "Work")

        @JvmField
        var FCommunicator: FragmentsComunicator? = null
    }
}