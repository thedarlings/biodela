import React, { Component } from 'react';
import './style.css';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", password: "" };
    
  }

  login() {
    fetch('http://192.168.0.9:8080/api/auth', {
      body: JSON.stringify({ username: this.state.username, password: this.state.password }),
      method: 'POST',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'text/plain'
      })
    }).then(response => {
      console.log(response)
      return response.json();
    }).then(data => {
      console.log({data})
      localStorage.setItem("sessiontoken", data.sessiontoken);
      this.props.authenticate();
    })
  }

  render() {
    return (
      <div className="centering">
        <div className="container">
          <p>Skriv in användarnamn och lösenord</p>
          <input
            type="text"
            placeholder="Användarnamn"
            onChange={event => this.setState({ username: event.target.value })}
          />
          <input
            type="password"
            placeholder="Lösenord"
            onChange={event => this.setState({ password: event.target.value })}
          />
          <button
            disabled={this.state.username.length < 2 || this.state.password.length < 2}
            onClick={() => this.login()}
          >
            Logga in
          </button>
        </div>
      </div>
    )
  }
}

export default Login;