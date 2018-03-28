import React, { Component } from 'react';
import './App.css';
import './general.css'
import Login from './Login'
import Main from './Main'

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
    //setTimeout(() => this.setState({pending: false}), 500);
    let token = localStorage.sessiontoken;
    let loggedIn = token && token.length > 0 ? true : false;
    this.setState({ loggedIn })
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
