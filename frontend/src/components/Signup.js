import React, { useEffect, useState } from 'react'
import Navbar from '../subComponents/Navbar'
import axios from 'axios'
import { Link, useNavigate } from 'react-router-dom'
import signup from '../images/signup.png'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


export default function Signup() {
  const initialValues = { username: "", email: "", password: "" }
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    let { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value })
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmit(true)
  }

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) {
      navigate("/dashboard")
    }
    if (Object.keys(formErrors).length === 0 && isSubmit) {
      onRegister();
    }
  }, [formErrors, isSubmit]);

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

  const validate = (values) => {
    const errors = {}
    const regexemail = /^([a-zA-Z0-9\._-]+)@([a-zA-Z0-9-])+\.([a-z]+)(.[a-z]+)?$/i;
    const regexuser = /^[a-zA-Z0-9_-]+$/i;
    const regexspaces = /^[^\s]+$/;
    values.username = values.username.trim();
    values.email = values.email.trim();
    if (!values.username) {
      errors.username = "Username is required"
    }
    else if (values.username.length < 5) {
      errors.username = "Username must be more than or equal to 5 characters"
    }
    else if (values.username.length > 15) {
      errors.username = "Username must be less than or equal to 15 characters"
    }
    else if (!regexspaces.test(values.username)) {
      errors.username = "Username must not contains spaces"
    }
    else if (!regexuser.test(values.username)) {
      errors.username = "Username can only contains alphanumerics, underscore and dash"
    }
    if (!values.email) {
      errors.email = "Email is required"
    }
    else if (!regexemail.test(values.email)) {
      errors.email = "This is not a valid email format"
    }
    if (!values.password) {
      errors.password = "Password is required"
    }
    else if (values.password.length < 5) {
      errors.password = "Password must be more than or equal to 5 characters"
    }
    else if (values.password.length > 20) {
      errors.password = "Password must be less than or equal to 20 characters"
    }
    return errors;
  }
  const onRegister = async () => {
    await axios.post("http://localhost:8080/auth/register", {
      name: formValues.username, email: formValues.email, password: formValues.password
    }).then((response) => {
      confirmToast("Please check your email to verify your account")
    }).catch((error) => {
      errorToast("User is already a member of ewallet")
    })
  }
  return (
    <>
      <Navbar />
      <div className='container-fluid' style={{ marginTop: '40px' }}>
        <form className='mx-auto p-3 rounded bg-white shadow-lg' style={{ width: '40%' }}>
          <h2 className='text-center'><img src={signup} alt="dashboard" style={{ width: '30px', marginRight: "10px" }} />Signup</h2>
          <div className="mb-3">
            <label htmlFor="username" className="form-label">Username</label>
            <input type="text" className="form-control rounded" id="username" name='username' value={formValues.username} onChange={handleChange} />
            <p className='text-danger'>{formErrors.username}</p>
          </div>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email address</label>
            <input type="text" name='email' className="form-control rounded" id="email" value={formValues.email} onChange={handleChange} />
            <p className='text-danger'>{formErrors.email}</p>
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">Password</label>
            <input type="password" name='password' className="form-control rounded" id="password" value={formValues.password} onChange={handleChange} />
            <p className='text-danger'>{formErrors.password}</p>
          </div>
          <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Submit</button>
          <p className='mt-3'>Already have an account? <Link to="/login">Login</Link></p>
        </form>
      </div>
      <ToastContainer style={{width: '35%', fontWeight: 'bold'}}/>
    </>
  )
}
