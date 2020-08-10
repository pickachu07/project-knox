/* eslint-disable react/prop-types */
import React, { useState} from 'react';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { makeStyles } from '@material-ui/styles';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import { useHistory} from 'react-router-dom';
import {
  Card,
  CardHeader,
  CardContent,
  CardActions,
  Divider,
  Grid,
  Button,
  TextField
} from '@material-ui/core';
import ActionService from '../../services/actionService';
import {CodeEditor} from './components/CodeEditor'
const useStyles = makeStyles(() => ({
  backdrop: {
    zIndex: 5 ,
    color: '#fff',
  },
  root: {
    margin : '20px',
    
  },
  executionList:{
    marginRight: '20px',
    float:'left'
  }
}));

function Alert(props) {
  return <MuiAlert
    elevation={6}
    variant="filled"
    {...props}
  />;
}


// eslint-disable-next-line react/no-multi-comp
const CreateAction = props => {
  const { className, location} = props;

  const classes = useStyles();

  const [values, setValues] = useState({
    id: location.isEdit?location.action.id:null,
    name: location.isEdit?location.action.name:'test',
    main: location.isEdit?location.action.main:'main',
    timeout: location.isEdit?location.action.timeout:'10000',
    memory: location.isEdit?location.action.memory:'256',
    runtime: location.isEdit?location.action.runtime:'nodejs:default',
    code: location.isEdit?location.action.code:`function main(params) {
      var result = params.value > 26000 ? true : false;
      return {
        isGreaterThan: result
      };
    }`,
    isLoading:false,
    isSuccess:false,
    isError:false,
    isComponentEdit:location.isEdit?location.isEdit:false
  });

  const handleCodeChange = code => {
    setValues({
      ...values,
      code:code
    });

  };

  const [open, setOpen] = React.useState({
    isOpen: false,
    message:'Operation Successful!'
  });
  const [errorOpen, setErrorOpen] = React.useState({
    isOpen: false,
    message:'There was an error!'
  });

  const showSuccess = (msg) => {
    setValues({
      ...values,
      isError:false
    })
    setOpen({
      isOpen:true,
      message:msg
    });
  };

  const showError = (msg) => {
    setValues({
      ...values,
      isError:true
    })
    setErrorOpen({
      isOpen:true,
      message:msg
    });
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen({
      isOpen:false,
      message:'',
    });
    setErrorOpen({
      isOpen:false,
      message:''
    });
  };




  const handleActionUpdate = () => {
    console.log(values);
    let editActionPromise = ActionService.updateAction(values.id,values.name,values.runtime,values.code,values.main,values.memory,values.timeout);
    
    editActionPromise.then(response =>{
      if(response.status == 200){
        console.log('Edited succesfully');
        showSuccess('Action updated successfully!');
      }else{
        console.log('Edit failed!'+response.status);
        showError('There was an Error: '+response.message);
      }
    }).catch(error =>{
      console.log('There was an Error: '+error);
      showError('There was an '+error);
    })
  }



  const handleActionSave = () =>{
    console.log(values);
    let saveActionPromise = ActionService.saveAction(values.name,values.runtime,values.code,values.main,values.memory,values.timeout);
    setValues({
      ...values,
      isLoading:true
    })
    saveActionPromise.then((response) =>{
      console.log(response);
      if(response.status === 200){ 
        showSuccess("Action saved successfully!");
        setValues({
          ...values,
          isLoading:false
        })
        history.push('/actions')
      }else{
        showError('Error saving Action! Cause: '+response.message);
      }
    }).catch(error => {
      console.log(error);
      showError();
      setValues({
        ...values,
        isLoading:false
      })
    })
  }
  const handleChange = event => {
    setValues({
      ...values,
      [event.target.name]: event.target.value
    });
  };

  const codeRuntime = [
    {
      value: 'nodejs:default',
      label: 'Javascript'
    },
    {
      value: 'python:default',
      label: 'Python'
    }
  ];

  const memoryList = [
    {
      value: '128',
      label: '128'
    },
    {
      value: '256',
      label: '256'
    },
    {
      value: '512',
      label: '512'
    },
    {
      value: '1024',
      label: '1024'
    },
  ]

  const history = useHistory();
  return (
    <div>
      
      <Grid 
        container
        spacing={3}
      >
        <Grid 
          item
          md={12}
          xs={12}
        >
          <Card
            className={clsx(classes.root, className)}
          >
            <form
              autoComplete="off"
              noValidate
            >
              <CardHeader
                subheader="Create or edit your action"
                title="Action"
              />
              <Divider />
              <CardContent>
                <Grid
                  container
                  spacing={3}
                >
                  <Grid
                    item
                    md={12}
                    xs={12}
                  >
                    <TextField
                      fullWidth
                      helperText="Please specify the action name"
                      label="Action Name"
                      margin="dense"
                      name="name"
                      onChange={handleChange}
                      required
                      value={values.name || ''}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid
                    item
                    md={6}
                    xs={12}
                  >
                    <TextField
                      fullWidth
                      label="Main"
                      margin="dense"
                      name="main"
                      onChange={handleChange}
                      required
                      value={values.main|| ''}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid
                    item
                    md={6}
                    xs={12}
                  >
                    <TextField
                      fullWidth
                      label="Timeout(in milliseconds)"
                      margin="dense"
                      name="timeout"
                      onChange={handleChange}
                      required
                      type="number"
                      value={values.timeout|| ''}
                      variant="outlined"
                    />
                  </Grid>
                  <Grid
                    item
                    md={6}
                    xs={12}
                  >
                    <TextField
                      fullWidth
                      label="Memory"
                      margin="dense"
                      name="memory"
                      onChange={handleChange}
                      type="number"
                      required
                      select
                      SelectProps={{ native: true }}
                      value={values.memory|| ''}
                      variant="outlined"
                    >
                      {memoryList.map(option => (
                        <option
                          key={option.value}
                          value={option.value}
                        >
                          {option.label}
                        </option>
                      ))}
                    </TextField>
                  </Grid>
                  <Grid
                    item
                    md={6}
                    xs={12}
                  >
                    <TextField
                      fullWidth
                      label="Runtime"
                      margin="dense"
                      name="runtime"
                      onChange={handleChange}
                      required
                      select
                      SelectProps={{ native: true }}
                      value={values.runtime || ''}
                      variant="outlined"
                    >
                      {codeRuntime.map(option => (
                        <option
                          key={option.value}
                          value={option.value}
                        >
                          {option.label}
                        </option>
                      ))}
                    </TextField>
                  </Grid>
                  <Grid
                    item
                    md={12}
                    xs={12}
                  >
                    <CodeEditor 
                      code={values.code}
                      sendData={handleCodeChange}
                      mode={values.runtime === 'nodejs:default' ? 'javascript' :'python'}
                    />
                  </Grid>
                </Grid>
              </CardContent>
              <Divider />
              <CardActions>
                {location.isEdit?
                  <Button
                    color="secondary"
                    onClick={handleActionUpdate}
                    variant="contained"
                    style={{'margin': '15px'}}
                  >
                Update Action
                  </Button>
                  :
                  <Button
                    color="primary"
                    onClick={handleActionSave}
                    variant="contained"
                    style={{'margin': '15px'}}
                  >
                Save Action
                  </Button>}
              </CardActions>
            </form>
          </Card>
          {values.isLoading && <Backdrop 
            className={classes.backdrop} 
            // eslint-disable-next-line react/jsx-boolean-value
            open={true}
          >
            <CircularProgress color="inherit" />
          </Backdrop>}
          
          <Snackbar
            autoHideDuration={6000}
            onClose={handleClose}
            open={open.isOpen}
          >
            <Alert
              onClose={handleClose}
              severity="success"
            >
              {open.message}
            </Alert>
          </Snackbar>
          <Snackbar
            autoHideDuration={6000}
            onClose={handleClose}
            open={errorOpen.isOpen}
          >
            <Alert
              onClose={handleClose}
              severity="error"
            >
              {errorOpen.message}
            </Alert>
          </Snackbar>
        </Grid>
    
      </Grid>
    </div>
  );
};

CreateAction.propTypes = {
  className: PropTypes.string
};

export default CreateAction;