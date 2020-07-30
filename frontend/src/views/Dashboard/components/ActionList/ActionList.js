import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import {
  Button,
} from '@material-ui/core';

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});




  
function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}
  
const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),


];
  
export default function ActionList(props) {
  
  const {executions, onSelected} = props;
  
  const classes = useStyles();
  
  const handleExecSelected = (exec)=>{
    console.log(exec);
    // onSelected({
    //   isSelected:true,
    //   selectedExecution:exec
    // })
  }


  return (
    <TableContainer component={Paper} style={{height: 400}}>
      <Table className={classes.table} aria-label="Execution List">
        <TableHead>
          <TableRow>
            <TableCell>Last updated</TableCell>
            <TableCell align="center">Running time</TableCell>
            <TableCell align="center">Status</TableCell>
            <TableCell align="center">Result status</TableCell>
            <TableCell align="center"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {executions ? executions.map((exec) => (  
            <TableRow key={exec.executionid}>
              <TableCell  scope="row">
                {exec.lastupdated}
              </TableCell>
              <TableCell align="center">{exec.metadata.duration}</TableCell>
              <TableCell align="center">{exec.status}</TableCell>
              <TableCell align="center">{exec.output.status}</TableCell>
              <TableCell align="center"><Button variant="outlined" onClick={() =>{onSelected(exec)}}>Details</Button></TableCell>
            </TableRow>
          )): <TableRow><Typography  style={{'text-align':'center'}}>'No Executions found'</Typography></TableRow>}
        </TableBody>
      </Table>
    </TableContainer>
  );
}