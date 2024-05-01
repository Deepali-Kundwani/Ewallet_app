import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function WithdrawLayout() {
    const initialValues = { value: 10 }
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
            onWithdraw(token);
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
        if (!values.value) {
            errors.value = "Amount is required"
        }
        else if(values.value<10){
            errors.value = "Minimum 10 Rs. can be transferred to bank"
        }
        return errors;
    }

    const onWithdraw = async (token) => {
        const amount = formValues.value;
        await axios.put(`http://localhost:8080/wallet/withdraw?value=${amount}`, null, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            confirmToast("Money withdraw successfull")
            formValues.value = 10;
        }).catch((error) => {
            if (error.response.data === 'Amount not available') {
                errorToast("Amount not available")
            }
        })
    }
    return (
        <>
            <div className="container rounded ms-2 shadow-lg" style={{ width: '90%', backgroundColor: '#f0f8ff' }}>
                <h2 className='mb-5 mt-4'>Withdraw</h2>
                <hr />
                <form>
                    <div className="mb-3">
                        <label htmlFor="exampleInputValue" className="form-label">Amount</label>
                        <input type="number" className="form-control" id="exampleInputValue" name='value' min="10" value={formValues.value} onChange={handleChange} style={{ border: "1px solid black", width: '50%' }} />
                    </div>
                    <p className='text-danger'>{formErrors.value}</p>
                    <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Withdraw to bank</button>
                </form>
            </div>
            <ToastContainer style={{width: '35%', fontWeight: 'bold'}}/>
        </>
    )
}
