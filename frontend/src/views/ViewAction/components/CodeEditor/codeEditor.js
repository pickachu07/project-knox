/* eslint-disable react/jsx-boolean-value */
import React from 'react';
import AceEditor from 'react-ace';

import 'ace-builds/webpack-resolver';
import 'ace-builds/src-noconflict/mode-java';
import 'ace-builds/src-noconflict/theme-github';

import {
  
  Paper,
 
} from '@material-ui/core';

 
class CodeEditor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      code: this.props.code ? this.props.code:`function main(params) {
        var result = params.value > 26000 ? true : false;
        return {
          isGreaterThan: result
        };
      }`,
      mode:this.props.mode ? this.props.mode : 'javascript'
    };

    this.handleCodeChange = this.handleCodeChange.bind(this);
  }
  
  handleCodeChange(code) {   
    this.setState({code: code});
    this.props.sendData(code);
  }
  
 
  render() {
    return (
      <Paper 
        elevation={1} 
        style={{ padding:'5px'}}>
        <AceEditor
          fontSize={14}
          width="100%"
          highlightActiveLine={true}
          mode={this.props.mode ? this.props.mode :"javascript"}
          name="code"
          onChange={this.handleCodeChange}
          onLoad={this.onLoad}
          placeholder="write your code here"
          readOnly={this.props.readOnly}
          setOptions={{
            enableBasicAutocompletion: true,
            enableLiveAutocompletion: true,
            enableSnippets: true,
            showLineNumbers: true,
            tabSize: 2,
          }}
          showGutter={true}
          showPrintMargin={true}
          theme="tomorrow"
          value={this.state.code}
        />
      </Paper>
    );
  }
}
export default CodeEditor;