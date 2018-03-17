import React, { Component } from 'react';
import './style.css';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = { username: "", password: "" };
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
            onClick={() => console.log(this.state.username.length )}
          >
            Logga in
          </button>
        </div>
      </div>
    )
  }
}

export default Login;