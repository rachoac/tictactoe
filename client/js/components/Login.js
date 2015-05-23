import React from 'react';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            count: props.initialCount || 0
        };

        setInterval( () => {
            this.update();
        }, 5000)
    }

    update() {
        this.setState({count: this.state.count + 1});
    }

    render() {
        return(<div>Welcome to {this.state.count}</div>);
    }
};

export default Login;