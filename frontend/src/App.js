import React, { Component } from 'react';
import './App.css';
import './general.css';
import Login from './containers/Login';
import Main from './containers/Main';


class App extends Component {
  constructor(props) {
    super(props);
    this.authenticate = this.authenticate.bind(this);
    this.logout = this.logout.bind(this);
    this.state = { loggedIn: false, pending: false };
  }

  componentDidMount() {
    this.authenticate();
  }

  authenticate() {
    let token = localStorage.sessiontoken;
    if (token && token.length > 0) {
      fetch('/api/auth/sessiontoken/' + token, {
        method: 'GET',
        mode: 'cors',
        headers: new Headers({
          'Content-Type': 'text/plain'
        })
      }).then(response => {
        console.log(response)
        if (response.status === 200) {
          this.setState({ loggedIn: true })
        }
      })
    }
  }

  logout() {
    fetch('/api/auth/sessiontoken/' + localStorage.sessiontoken, {
      method: 'DELETE',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'text/plain'
      })
    }).then(response => {
      if (response.status === 200) return response.text();
    }).then(data => {
      localStorage.clear("sessiontoken");
      this.setState({ loggedIn: false })
    })
    
  }



  render() {
    if (this.state.pending) return <div>Pending...</div>

    return (
      <div>
        { this.state.loggedIn ? <Main logout={this.logout} /> : <Login authenticate={this.authenticate} /> }
      </div>
    );
  }
}

export default App;
