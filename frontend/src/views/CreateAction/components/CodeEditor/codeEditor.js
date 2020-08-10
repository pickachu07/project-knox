import React from 'react';
import AceEditor from 'react-ace';
 
import 'ace-builds/src-noconflict/mode-java';
import 'ace-builds/src-noconflict/theme-github';



import {
  
  Paper,
 
} from '@material-ui/core';


const code = `function add(a, b) {
  return a + b;
}
`;
 
class CodeEditor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      code: this.props.code ? this.props.code:`function onLoad(editor) {
        console.log("i've loaded");
      }`,
    };

    this.handleCodeChange = this.handleCodeChange.bind(this);
  }
  
  handleCodeChange(code) {
    
    this.setState({code: code});
    this.props.sendData(code);
  }
  
 
  render() {
    return (
      <Paper elevation={1} style={{ padding:'5px'}}>
        <AceEditor
          placeholder="write your code here"
          mode={this.props.mode || 'javascript'}
          theme="tomorrow"
          name="code"
          width="100%"
          onLoad={this.onLoad}
          onChange={this.handleCodeChange}
          fontSize={14}
          showPrintMargin={true}
          showGutter={true}
          highlightActiveLine={true}
          value={this.state.code}
          setOptions={{
            enableBasicAutocompletion: true,
            enableLiveAutocompletion: true,
            enableSnippets: true,
            showLineNumbers: true,
            tabSize: 2,
          }}
        />
      </Paper>
    );
  }
}
export default CodeEditor;