import './App.css';
import React, { useState, useEffect } from 'react';

function App() {

  const [posts, setPosts] = useState([]);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [supervisor, setSupervisor] = useState("");
  const [empStat, setEmpStat] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch('http://ec2-52-90-251-91.compute-1.amazonaws.com:8080/api/supervisors')
      .then((response) => response.json())
      .then((data) => {
            console.log(data);
            setPosts(data);
      })
      .catch((err) => {
            console.log(err.message);
      });
  }, []);


  let newEmp = async (e) => {
    e.preventDefault();
    let resp = await fetch("http://ec2-52-90-251-91.compute-1.amazonaws.com:8080/api/submit", {
      "method": "POST",
      "headers": { 'Content-Type': 'application/json' },
      "body": JSON.stringify({
        firstName: firstName,
        lastName: lastName,
        email: email,
        phoneNumber: phoneNumber,
        supervisor: supervisor
      }),
    });
    let respJson = await resp.json();
    setEmpStat(respJson);
    if (resp.ok) {
      setMessage("Request processed successfully.");
      console.log(respJson)
      console.log("Request processed successfully.")
    } else {
      setMessage("Error: First Name, Last Name, and Supervisor are required.");
      console.log(respJson)
      console.log("Error: First Name, Last Name, and Supervisor are required.")
    }
  };


  return (
    <div>
      <h1 classname="field">Employee Management System</h1>
      <h2  classname="field">Add Employee</h2>
      <div className="field">
        {empStat.data && empStat.map((emp) => {
          return (
            <div>
            <div>
              First Name: {emp.firstName}, Last Name: {emp.lastName}, Email: {emp.email},
              Phone Number: {emp.phoneNumber}, Supervisor: {emp.supervisor}
            </div>
            <div classname="field">
               {message}
            </div>
            </div>
          );
        })}
      </div>
      <div>
        <form onSubmit={newEmp}>
          <div className="field">
            <label>
              First Name:&nbsp;
              <input
                type="text"
                value={firstName}
                placeholder="First Name"
                onChange={(e) => setFirstName(e.target.value)}
              />
            </label>
          </div>
          <div className="field">
            <label>
              Last Name:&nbsp;
              <input
                type="text"
                value={lastName}
                placeholder="Last Name"
                onChange={(e) => setLastName(e.target.value)}
              />
            </label>
          </div>
          <div className="field">
            <label>
              Email:&nbsp;
              <input
                type="text"
                value={email}
                placeholder="Email"
                onChange={(e) => setEmail(e.target.value)}
              />
            </label>
          </div>
          <div className="field">
            <label>
              Phone Number:&nbsp;
              <input
                type="text"
                value={phoneNumber}
                placeholder="Phone Number"
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </label>
          </div>
          <div className="field">
            <label>
              Supervisor:&nbsp;
              <input
                type="text"
                value={supervisor}
                placeholder="Supervisor"
                onChange={(e) => setSupervisor(e.target.value)}
              />
            </label>
          </div>
          <div className="field">
            <button type="submit">Add New Employee</button>
          </div>
        </form>
      </div>
      <h2  classname="field">Current Employees</h2>
      <div className="field">
        {posts.map((post) => {
          return (
            <div>
              {post.jurisdiction} - {post.lastName}, {post.firstName}
            </div>
          );
        })}
      </div>
    </div>
  );

}

export default App;
