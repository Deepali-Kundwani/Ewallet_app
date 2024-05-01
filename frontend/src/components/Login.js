import React, { useEffect, useState } from 'react';
import Navbar from '../subComponents/Navbar';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom'
import login from '../images/login.png'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


export default function Login(props) {
  const initialValues = { email: "", password: "" }
  let ct = 0;
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();


  const handleChange = (e) => {
    let { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value })
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmit(true);
  }

  useEffect(() => {

    const token = localStorage.getItem('token')
    if (token) {
      navigate("/dashboard")
    }
    if (Object.keys(formErrors).length === 0 && isSubmit) {
      onLogin();
    }
    else {
      if (location.pathname === '/login/verified' && ct === 0) {
        confirmToast("User successfully verified");
        ct = ct + 1;
      }
      else if(location.pathname === '/login/unverified' && ct === 0){
        errorToast("Verification link is not valid")
        ct = ct + 1;
      }
    }
  }, [formErrors, isSubmit]);

  const errorToast = (message) => {
    toast.error(message, {
      position: "top-center",
      autoClose: 3500,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "colored",
    });
  }

  const confirmToast = (message) => {
    toast.success(message, {
      position: "top-center",
      autoClose: 3500,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "colored",
    });
  }

  const validate = (values) => {
    const errors = {}
    const regex = /^([a-zA-Z0-9\._-]+)@([a-zA-Z0-9-])+\.([a-z]+)(.[a-z]+)?$/i;
    values.email = values.email.trim();
    if (!values.email) {
      errors.email = "Email is required"
    }
    else if (!regex.test(values.email)) {
      errors.email = "This is not a valid email format"
    }
    if (!values.password) {
      errors.password = "Password is required"
    }
    else if (values.password.length < 5) {
      errors.password = "Password must be more than or equal to 5 characters"
    }
    else if(values.password.length > 20){
      errors.password = "Password must be less than or equal to 20 characters"
    }
    return errors;
  }
  const onLogin = async () => {
    await axios.post("http://localhost:8080/auth/login", {
      email: formValues.email, password: formValues.password
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    }).then((response) => {
      const { jwtToken } = response.data;
      localStorage.setItem('token', jwtToken);
      navigate("/dashboard");
    }).catch((error) => {
      errorToast("Credentials Invalid")
    })
  }
  return (
    <>
      <Navbar />
      <div className='container-fluid' style={{ marginTop: '40px' }}>
        <form className='mx-auto p-3 bg-white rounded shadow-lg' style={{ width: '40%' }}>
          <h2 className='text-center'><img src={login} alt="dashboard" style={{ width: '30px', marginRight: "10px" }} />Login</h2>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email address</label>
            <input type="text" name='email' className="form-control" id="email" value={formValues.email} onChange={handleChange} />
            <p className='text-danger'>{formErrors.email}</p>
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">Password</label>
            <input type="password" name='password' className="form-control" id="password" value={formValues.password} onChange={handleChange} />
            <p className='text-danger'>{formErrors.password}</p>
          </div>
          <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Submit</button>
          <p className='mt-3'>Don't have an account? <Link to="/signup">Signup</Link></p>
        </form>
      </div>
      <ToastContainer style={{width: '35%', fontWeight: 'bold'}}/>
    </>
  )
}
