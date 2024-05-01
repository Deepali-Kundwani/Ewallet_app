import { useEffect, useState } from "react"
import React from 'react'
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function TransferLayout() {
    const initialValues = { email: "", amount: 10 }
    const [formValues, setFormValues] = useState(initialValues);
    const [formErrors, setFormErrors] = useState({});
    const [isSubmit, setIsSubmit] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormValues({ ...formValues, [name]: value })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setFormErrors(validate(formValues));
        setIsSubmit(true);
    }

    useEffect(() => {
        if (Object.keys(formErrors).length === 0 && isSubmit) {
            const token = localStorage.getItem('token');
            onTransfer(token);
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
        if (!values.amount) {
            errors.amount = "Amount is required"
        }
        else if(values.amount<10){
            errors.amount = "Minimum 10 Rs. can be transferred"
        }
        return errors;
    }

    const onTransfer = async (token) => {
        await axios.post("http://localhost:8080/wallet/transfer", {
            email: formValues.email, amount: formValues.amount
        }, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            if (response.data === `Insert receiver's email`) {
                errorToast("Do not enter your own email")
            }
            else {
                confirmToast("Money transfer successfull")
                formValues.amount = 10;
                formValues.email = "";
            }
        }).catch((error) => {
            if (error.response.data === 'Amount not available in wallet') {
                errorToast("Amount not available")
            }
            else {
                errorToast("Receiver is not an ewallet user")
            }
        })
    }

    return (
        <>
            <div className="container rounded ms-2 shadow-lg" style={{ width: '90%', backgroundColor: '#f0f8ff' }}>
                <h2 className='mb-5 mt-4'>Transfer</h2>
                <hr />
                <form>
                    <div className="mb-3">
                        <label htmlFor="exampleInputEmail1" className="form-label">Receiver's email</label>
                        <input type="text" name="email" className="form-control" id="exampleInputEmail1" value={formValues.email} onChange={handleChange} style={{ border: "1px solid black", width: '50%' }} />
                        <p className='text-danger'>{formErrors.email}</p>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="exampleInputInteger" className="form-label">Amount</label>
                        <input type="number" name="amount" className="form-control" id="exampleInputInteger" value={formValues.amount} onChange={handleChange} min="10" style={{ border: "1px solid black", width: '50%' }} />
                        <p className='text-danger'>{formErrors.amount}</p>
                    </div>
                    <button type="submit" onClick={handleSubmit} className="btn btn-primary">Transfer</button>
                </form>
            </div>
            <ToastContainer style={{width: '35%', fontWeight: 'bold'}}/>
        </>
    )
}
