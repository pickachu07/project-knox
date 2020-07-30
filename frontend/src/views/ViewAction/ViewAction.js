/* eslint-disable react/prop-types */
import ArrowRightIcon from '@material-ui/icons/ArrowRight';
import React, { useState, useEffect } from 'react';
import Icon from '@material-ui/core/Icon';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { makeStyles } from '@material-ui/styles';
import Typography from '@material-ui/core/Typography';
import { useHistory} from 'react-router-dom';
import Chip from '@material-ui/core/Chip';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
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
import {LogViewer} from './components/LogViewer';
import ExecutionService from '../../services/executionService';
import {CodeEditor} from './components/CodeEditor'
import { ActionList } from 'views/Dashboard/components';
const useStyles = makeStyles(() => ({
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
const ViewAction = props => {
  const { className, location} = props;

  const classes = useStyles();

  const [values, setValues] = useState({
    id:location.isEdit?location.action.id:'',
    name: location.isEdit?location.action.name:'test',
    main: location.isEdit?location.action.main:'main',
    timeout: location.isEdit?location.action.timeout:'100',
    memory: location.isEdit?location.action.memory:'128',
    runtime: location.isEdit?location.action.runtime:'js',
    code: location.isEdit?location.action.code:'function onLoad(editor) {}',
    isLoading:false,
    isSuccess:false,
    isError:false,
    isComponentEdit:location.isEdit?location.isEdit:false,
    executions:[]
  });

  const handleCodeChange = code => {
    setValues({
      ...values,
      code:code
    });

  };

  const logs = [
    'somestring',
    'somestring',
    'somestring',
  ]


  const [selExec,setSelExec] = React.useState({
    isSelected:false,
  });

  const handleSelectExecution = (exec) => {
    console.log(exec);
    setSelExec({
      isSelected:true,
      executionInstance:exec
    });
  }

  const handleOpenDialog = () => {
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };




  const [openDialog, setOpenDialog] = React.useState(false);

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

  const handleDeleteAction = () => {
    console.log(values);
    let deleteActionPromise = ActionService.deleteAction(values.id);
    
    deleteActionPromise.then(response =>{
      if(response.status == 200){
        console.log('Deleted succesfully');
        showSuccess('Action Deleted!');
        handleCloseDialog();
        history.push('/actions');
      }else{
        console.log('Delete failed!'+response.status);
        showError('There was an Error: '+response.message);
        handleCloseDialog();
      }
    }).catch(error =>{
      console.log('There was an Error: '+error);
      showError('There was an '+error);
      handleCloseDialog();
    })
  } 




  const handleEditAction = () => {
    history.push({pathname:'/create-action',isEdit:true,action:values });
    console.log(values);
    console.log('Edit');
  }

  const fetchExecutions = () => {
    let executions = ExecutionService.fetchExecutions();
    console.log(executions);
    return executions;
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
    console.log('Edit:'+location.isEdit);
    console.log(location.isEdit);
    let executions = fetchExecutions();
    setValues({
      ...values,
      executions:executions
    });
  }, []);


  const history = useHistory();


  return (
    <div>
      <Grid 
        container
        spacing={0}
      >
        <Grid 
          item
          md={6}
          xs={6}
        >
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
                      disabled
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
                      disabled
                      onChange={handleChange}
                      
                      value={values.main || ''}
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
                      
                      disabled
                      type="number"
                      value={values.timeout || ''}
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
                      disabled
                      value={values.memory || ''}
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
                      disabled
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
                      readOnly={true}
                    />
                  </Grid>
                </Grid>
              </CardContent>
              <Divider />
              <CardActions>
                <Button
                  color="secondary"
                  onClick={handleEditAction}
                  style={{'margin': '15px'}}
                  variant="contained"
                >
                Edit Action
                </Button>
                <Button
                  
                  onClick={handleOpenDialog}
                  style={{'margin': '15px','color':'red'}}
                  variant="contained"
                >
                Delete Action
                </Button>
              </CardActions>
            </form>
          </Card>
          
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
    
        <Grid 
          item  
          md={6}
          xs={6}
        >
          <Grid 
            item 
            md={12}
            xs={12}
          >
            <Card className={clsx(classes.root, className)}>
              <CardHeader
                subheader="View action executions"
                title="Executions"
              />
              <Divider />
              <ActionList executions={values.executions} onSelected={handleSelectExecution}/>
            </Card>
          </Grid>
          <Grid 
            item 
            md={12}
            xs={12}
          >
            <Card className={clsx(classes.root, className)}>
              {/* <LogViewer logs={logs}/> */}
              <CardHeader
                subheader="view execution details"
                title="Execution details"
              />
              <Divider />
              <CardContent style={{'height':'320px'}}>
                <Grid
                  container
                  spacing={2}
                >
                  <Grid item>
                    
                  </Grid>
                  <Grid
                    container
                    item
                    sm
                    xs={12}
                  >
                    <Grid
                      container
                      direction="column"
                      item
                      spacing={2}
                      xs
                    >
                      <Grid
                        item
                        xs
                      >
                        <Typography
                          variant="body1"
                        > <Icon className="fa fa-plus-circle" />
                  {selExec.isSelected?+' Id: '+selExec.executionInstance.executionid:'no execution selected'}
                        </Typography>
                        <Typography
                          gutterBottom
                          variant="body2">
                        {selExec.isSelected?'Id: '+selExec.executionInstance.executionid:'no execution selected'}
                        </Typography>
                        <Typography
                          color="textSecondary"
                          variant="body2"
                        >
                  ID: 1030114
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Typography
                          style={{ cursor: 'pointer' }}
                          variant="body2"
                        >
                  Remove
                        </Typography>
                      </Grid>
                    </Grid>
                    <Grid item>
                      <Typography variant="overline" display="block">{selExec.isSelected?<Chip size="small" color="secondary" label={selExec.executionInstance.status}/>:''}</Typography>
                    </Grid>
                    
                  </Grid>
                </Grid>
              </CardContent>
              <Divider />
              <CardActions className={classes.actions}>
                <Button
                  color="primary"
                  size="small"
                  variant="text"
                  style={{'float':'right'}}
                >
                  View Logs <ArrowRightIcon />
                </Button>
              </CardActions>
            </Card>
          </Grid>
        </Grid>
      </Grid>
      <Dialog
        open={openDialog}
        onClose={handleCloseDialog}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{'Confirm Deletion'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are sure you want to delete the Action.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary">
            Cancel
          </Button>
          <Button onClick={handleDeleteAction} color="primary" style={{'color':'red'}} autoFocus>
            Delete
          </Button>
        </DialogActions>
      </Dialog>

    </div>
  );
};

ViewAction.propTypes = {
  className: PropTypes.string
};

export default ViewAction;