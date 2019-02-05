package edu.hayes.movieroom.movie;

import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

import edu.hayes.movieroom.R;
import edu.hayes.movieroom.db.DirectorDao;
import edu.hayes.movieroom.db.MovieDao;
import edu.hayes.movieroom.db.MovieDatabase;
import edu.hayes.movieroom.models.Director;
import edu.hayes.movieroom.models.Movie;

/**
 * @author James Hayes
 */
public class CreateMovieActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mYearEditText;
    private EditText mRunTimeEditText;
    private EditText mCollectionEditText;
    private EditText mDirectorFirstNameEditText;
    private EditText mDirectorLastNameEditText;
    private Button  mSaveButton;
    private MovieDao mMovieDao;
    private DirectorDao mDirectorDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_movie);



        mMovieDao = MovieDatabase.getDatabase(this).movieDao();
        mDirectorDao = MovieDatabase.getDatabase(this).directorDao();

        mTitleEditText = findViewById(R.id.titleEditText);
        mYearEditText = findViewById(R.id.yearEditText);
        mRunTimeEditText = findViewById(R.id.runTimeEditText);
        mCollectionEditText = findViewById(R.id.collectionEditText);
        mDirectorFirstNameEditText = findViewById(R.id.directorFirstNameEditText);
        mDirectorLastNameEditText = findViewById(R.id.directorLastNameEditText);
        mSaveButton = findViewById(R.id.saveButton);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString();
                String year = mYearEditText.getText().toString();
                String runTime = mRunTimeEditText.getText().toString();
                String collection = mCollectionEditText.getText().toString();
                String directorFirstName = mDirectorFirstNameEditText.getText().toString();
                String directorLastName = mDirectorLastNameEditText.getText().toString();

                if (title.length() == 0)  {
                    Toast.makeText(CreateMovieActivity.this, "Movie title required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (collection.equals("no") || collection.equals("yes") || collection.length() == 0) {
                } else {
                    Toast.makeText(CreateMovieActivity.this, "Collection entry not valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (directorLastName.length() == 0) {
                    Toast.makeText(CreateMovieActivity.this,"Director last name required",Toast.LENGTH_SHORT).show();
                }

                Movie newMovie = new Movie();
                Director newDirector = new Director();

                newDirector.setFirstName(directorFirstName);
                newDirector.setLastName(directorLastName);

                // first insert the director in to database
                try {
                    mDirectorDao.insert(newDirector);
                    setResult(RESULT_OK);
                    finish();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(CreateMovieActivity.this, "A director with same last name already exists.", Toast.LENGTH_SHORT).show();
                }

                newDirector = mDirectorDao.getDirectorByLastName(directorLastName);
                int dId = newDirector.getId();

                newMovie.setTitle(title);
                newMovie.setYear(year);
                newMovie.setRunTime(runTime);
                newMovie.setCollection(collection);
                newMovie.setDirectorId(dId);

                try {
                    mMovieDao.insert(newMovie);
                    setResult(RESULT_OK);
                    finish();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(CreateMovieActivity.this, "A movie with same title and director exists.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
