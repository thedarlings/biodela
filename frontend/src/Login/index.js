import React, { Component } from 'react';
import './style.css';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", password: "", loginFailed: false };
    
  }

  login() {
    fetch('https://biodela.herokuapp.com/api/auth', {
      body: JSON.stringify({ username: this.state.username, password: this.state.password }),
      method: 'POST',
      mode: 'cors',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    })
    .then(response => {
      if (response.status === 200){
        response.json().then(data => {
          localStorage.setItem("sessiontoken", data.sessiontoken);
          this.props.authenticate();
        })
      } else if (response.status === 401){
        this.setState({ loginFailed: true });
        localStorage.clear("sessiontoken");
      }
      return response;
    }).catch(error => {
      console.log("Fel fel fel fel fel fel!")
      console.log(error.message)
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
          {this.state.loginFailed && <div>Fel vid inloggning</div>}
        </div>
      </div>
    )
  }
}

export default Login;