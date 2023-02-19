import { AppBar, Toolbar, Typography } from '@mui/material';
import './App.css';
import Carlist from './components/Carlist';

function App() {
  return (
    <div className="App">
      <AppBar position='sticky'>
        <Toolbar>
          <Typography variant='h6'>
            Carshop
          </Typography>
        </Toolbar>
      </AppBar>

      <Carlist />
    </div>
  );
}

export default App;
