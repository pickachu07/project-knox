import React, { useState, useEffect } from 'react';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { makeStyles } from '@material-ui/styles';
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
  root: {
    margin : '20px',
    height : '900px'
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
  const { className, location, ...rest } = props;

  const classes = useStyles();

  const [values, setValues] = useState({
    name: location.isEdit?location.action.name:'test',
    main: location.isEdit?location.action.main:'main',
    timeout: location.isEdit?location.action.timeout:'100',
    memory: location.isEdit?location.action.memory:'128',
    runtime: location.isEdit?location.action.runtime:'js',
    code: location.isEdit?location.action.code:'function onLoad(editor) {}',
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

  const [open, setOpen] = React.useState(false);

  const showSuccess = () => {
    setValues({
      ...values,
      isError:false
    })
    setOpen(true);
  };

  const showError = () => {
    setValues({
      isError:true
    })
    setOpen(true);
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

  const handleActionUpdate = () => {
    console.log(values);
  }



  const handleActionSave = () =>{
    console.log(values);
    ActionService.saveAction(values.name,values.runtime,values.code,values.main,values.memory,values.timeout);
    showError();
  }
  const handleChange = event => {
    setValues({
      ...values,
      [event.target.name]: event.target.value
    });
  };

  const codeRuntime = [
    {
      value: 'js',
      label: 'Javascript'
    }
  ];

  useEffect(() => {
    // Update the document title using the browser API
    console.log('Edit:'+location.isEdit);
    console.log(location.action);
  });



  return (
    <div>
      <Grid container spacing={3}>
        <Grid item  md={12}
          xs={12}>
          <Card
            className={clsx(classes.root, className)}
          >
            <form
              autoComplete="off"
              noValidate
            >
              <CardHeader
                subheader="Create or Edit your actions"
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
                      label="Action name"
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
                      label="main class"
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
                      label="Timeout"
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
                      value={values.memory|| ''}
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
                      label="Runtume"
                      margin="dense"
                      name="runtime"
                      onChange={handleChange}
                      required
                      select
                      // eslint-disable-next-line react/jsx-sort-props
                      SelectProps={{ native: true }}
                      value={values.state || ''}
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
                  >
                Update Action
                  </Button>:
                  <Button
                    color="primary"
                    onClick={handleActionSave}
                    variant="contained"
                  >
                Save Action
                  </Button>}
              </CardActions>
            </form>
          </Card>
          <Snackbar
            autoHideDuration={6000}
            onClose={handleClose}
            open={open}
          >
            <Alert
              onClose={handleClose}
              severity={values.isError?'error':'success'}
            >
              {values.isError? 'Error while saving Action.' : 'Action saved successfully!'}
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