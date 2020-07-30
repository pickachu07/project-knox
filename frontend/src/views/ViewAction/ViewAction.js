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
import ButtonBase from '@material-ui/core/ButtonBase';
import { useHistory} from 'react-router-dom';
import Chip from '@material-ui/core/Chip';
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
import lifecycle from 'react-pure-lifecycle';
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
  const { className, location, ...rest } = props;

  const classes = useStyles();

  const [values, setValues] = useState({
    id:'',
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
    console.log("exec selected");
    console.log(exec);
    setSelExec({
      isSelected:true,
      executionInstance:exec
    });
  }
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
                      required
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
                <Button
                  color="secondary"
                  onClick={handleEditAction}
                  style={{'margin': '15px'}}
                  variant="contained"
                >
                Edit Action
                </Button>
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
    </div>
  );
};

ViewAction.propTypes = {
  className: PropTypes.string
};

export default ViewAction;