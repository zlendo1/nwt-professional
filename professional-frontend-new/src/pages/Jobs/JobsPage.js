import React, { useState, useEffect } from 'react';
import './JobsPage.css';

const JobsPage = () => {
  const [job, setJob] = useState({
    title: '',
    description: '',
    location: '',
    employmentType: '',
    publishDate: '',
    expirationDate: ''
  });

  const [jobsList, setJobsList] = useState([]);

  const handleJobSubmit = () => {
    console.log('Job created:', job);
    setJobsList([...jobsList, job]);
    setJob({
      title: '',
      description: '',
      location: '',
      employmentType: '',
      publishDate: '',
      expirationDate: ''
    });
  };

  const handleAddTest = () => {
    console.log('Add Test clicked');
  };

  useEffect(() => {
    const mockJobs = [
      { title: 'Frontend Developer', location: 'Sarajevo', employmentType: 'FULL_TIME' },
      { title: 'Project Manager', location: 'Mostar', employmentType: 'CONTRACT' }
    ];
    setJobsList(mockJobs);
  }, []);

  return (
    <div className="jobs-page">
      <section className="job-form box-darker">
        <h2>Create a Job</h2>

        <div className="form-group">
          <input
            type="text"
            placeholder="Job Title"
            value={job.title}
            onChange={(e) => setJob({ ...job, title: e.target.value })}
          />
        </div>

        <div className="form-group">
          <textarea
            placeholder="Job Description"
            value={job.description}
            onChange={(e) => setJob({ ...job, description: e.target.value })}
          />
        </div>

        <div className="form-group">
          <input
            type="text"
            placeholder="Location"
            value={job.location}
            onChange={(e) => setJob({ ...job, location: e.target.value })}
          />
        </div>

        <div className="form-group">
          <select
            value={job.employmentType}
            onChange={(e) => setJob({ ...job, employmentType: e.target.value })}
          >
            <option value="">Select Employment Type</option>
            <option value="FULL_TIME">Full Time</option>
            <option value="PART_TIME">Part Time</option>
            <option value="CONTRACT">Contract</option>
            <option value="TEMPORARY">Temporary</option>
            <option value="INTERNSHIP">Internship</option>
          </select>
        </div>

        <div className="form-group">
          <input
            type="date"
            value={job.publishDate}
            onChange={(e) => setJob({ ...job, publishDate: e.target.value })}
          />
        </div>

        <div className="form-group">
          <input
            type="date"
            value={job.expirationDate}
            onChange={(e) => setJob({ ...job, expirationDate: e.target.value })}
          />
        </div>

        <div className="job-actions">
          <button onClick={handleJobSubmit}>Create Job</button>
          <button className="add-test" onClick={handleAddTest}>➕ Add Test</button>
        </div>
      </section>

      <section className="jobs-list box-dark">
        <h2>Available Jobs</h2>
        {jobsList.length === 0 ? (
          <p className="placeholder">No jobs available yet.</p>
        ) : (
          <ul>
            {jobsList.map((job, index) => (
              <li key={index}>
                <strong>{job.title}</strong> – {job.location} ({job.employmentType})
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
};

export default JobsPage;
