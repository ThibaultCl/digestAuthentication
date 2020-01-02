import React, {Component} from 'react';
import './App.css';
import md5 from 'js-md5';

class App extends Component{

  constructor(props){
    super(props)
    this.state = {display:""};

    // Send a request to register a new user
    let http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/user");
    http.setRequestHeader("Content-Type", "application/json");
    let data = '{"login":"toto","password":"toto"}';

    http.onreadystatechange = (e) => {
      if(http.readyState===4 && http.status===200){
        // request OK
        console.log("A new user has been register");

        //Send a digest request
        this.digestRequest("http://localhost:8080/", "/", "GET", null, "toto", "toto");
      }
      else if (http.readyState===4 && http.status!==200){
        // request failed
        console.log("Error : Can't register the new user !");
        console.log(http.status);
        console.log(http.responseText);
        this.setState({display:http.response});
      }
    }
    
    http.send(data);
    console.log("request sent to http://localhost:8080/user");
  }

  // Send a Http request using the digest authentication protocol
  // url : string, url of the request (example : "localhost:8080/home")
  // digestUri : string, uri used in digest hash (example : "/home")
  // requestMethod : string, type of the request ("GET", "POST", ...)
  // data : string, data to send in the body of the request
  // username : string, username for digest authentication
  // password : string, password for digest authentication
  digestRequest(url, digestUri,requestMethod, data, username, password) {
    let req = new XMLHttpRequest();
    req.open("GET", url);

    req.onreadystatechange = (e) => {
      if(req.readyState==4 && req.status==200){
        // successful request, get data here 
        console.log(req.response);
        this.setState({display:req.response});
      }
      else if(req.readyState==4 && req.status==401){
        // Unauthorized request, create digest request here
        let authHeader = req.getResponseHeader("WWW-Authenticate")
          .substring(7)
          .split(', ')
          .reduce((result, current) => {
            let [name, value] = current.split('"');
            result[name.substring(0,name.length-1)] = value;
            return result;
          }, {});
        
        let cnonce = "undefined";
        let nc = '00000001';
        let ha1 = md5(username + ':' + authHeader['realm'] + ':' + password);
        let ha2 = md5(requestMethod + ':' + digestUri);
        let response = md5(ha1 + ':' + authHeader['nonce'] + ':' + nc + ':' + cnonce + ':' + authHeader['qop'] + ':' + ha2);

        let req2 = new XMLHttpRequest();
        req2.open(requestMethod, url);

        let authorizationValue = "Digest " + 
          "username=" + username + "," +
          "realm=" + authHeader['realm'] + "," +
          "nonce=" + authHeader['nonce'] + "," +
          "uri=" + digestUri + "," +
          "algorithm=" + "MD5" + "," +
          "qop=" + authHeader['qop'] + "," +
          "nc=" + nc +  "," +
          "cnonce=" + cnonce + "," +
          "response=" + response;

        req2.setRequestHeader("Authorization", authorizationValue);

        req2.onreadystatechange = (e) => {
          if(req2.readyState==4 && req2.status==200){
            // digest request is successful, get data here
            console.log(req2.response);
            this.setState({display:req2.response});
          }
          else if(req2.readyState==4 && req2.status!=200){
            // request failed
            console.log(req2.response);
            this.setState({display:req2.response});
          }
        }

        req2.send(data);
      }
      else if(req.readyState==4 && req.status==40 && req.status==200){
        // request failed
        console.log(req.response);
        this.setState({display:req.response});
      }
    }

    req.send();
    console.log("request sent to http://localhost:8080/");
  }

  render(){
    let display = this.state.display;
    return (
    <div className="App">
      {display}
    </div>
    );
  }
}

export default App;
