package com.example.sqlite.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sqlite.R;
import com.example.sqlite.database.LocalData;
import com.example.sqlite.model.Student;

import java.util.ArrayList;

public class StudentDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerStudentDetails;
    Context context;
    ArrayList<Student> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        recyclerStudentDetails = findViewById(R.id.recylerStudentDetails);
        context = this;
        recyclerStudentDetails.setLayoutManager(new LinearLayoutManager(context));

        fetchData();

    }

    private void fetchData() {
        LocalData localData = new LocalData(context);
        SQLiteDatabase sqLiteDatabase = localData.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * FROM student", null);

        if (cursor.moveToFirst()) {

            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setPhone(cursor.getString(2));
                list.add(student);


            } while (cursor.moveToNext());
        }
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
        recyclerStudentDetails.setAdapter(new StudentDetailsAdapter(context, list));

    }

    private class StudentDetailsAdapter extends RecyclerView.Adapter<StudentDetailsAdapter.StudentDetailsViewHolder> {

        Context context;
        ArrayList<Student> list;


        public StudentDetailsAdapter(Context context, ArrayList<Student> list) {

            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public StudentDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.template_student_details, parent, false);
            StudentDetailsViewHolder studentDetailsViewHolder = new StudentDetailsViewHolder(view);
            return studentDetailsViewHolder;
        }

        @Override
        public void onBindViewHolder(StudentDetailsViewHolder holder, int position) {
            Student obj = list.get(position);
            holder.txtId.setText(Integer.toString(obj.getId()));
            holder.txtName.setText(obj.getName());
            holder.txtPhone.setText(obj.getPhone());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class StudentDetailsViewHolder extends RecyclerView.ViewHolder {
            TextView txtId, txtName, txtPhone;
            public StudentDetailsViewHolder(View itemView) {
                super(itemView);
                txtId = itemView.findViewById(R.id.txtId);
                txtName = itemView.findViewById(R.id.txtName);
                txtPhone = itemView.findViewById(R.id.txtPhone);

            }
        }
    }


    // Adapter Class

}
