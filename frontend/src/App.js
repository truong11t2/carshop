import { AppBar, Toolbar, Typography } from '@mui/material';
import './App.css';

function App() {
  return (
    <div className="App">
      <AppBar position='static'>
        <Toolbar>
          <Typography variant='h6'>
            Carshop
          </Typography>
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default App;
