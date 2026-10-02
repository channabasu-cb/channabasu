const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;

const DATA_DIR = path.join(__dirname, 'data');
const PROGRESS_FILE = path.join(DATA_DIR, 'progress.json');

// Ensure data directory and progress file exist
if (!fs.existsSync(DATA_DIR)) fs.mkdirSync(DATA_DIR, { recursive: true });
if (!fs.existsSync(PROGRESS_FILE)) {
  fs.writeFileSync(PROGRESS_FILE, JSON.stringify({
    completedTasks: [],
    notes: {},
    startDate: new Date().toISOString().split('T')[0],
    lastActive: new Date().toISOString().split('T')[0]
  }, null, 2));
}

app.use(express.json());
app.use(express.static(path.join(__dirname, 'public')));

// GET progress
app.get('/api/progress', (req, res) => {
  try {
    const data = JSON.parse(fs.readFileSync(PROGRESS_FILE, 'utf8'));
    res.json(data);
  } catch (err) {
    res.status(500).json({ error: 'Failed to read progress' });
  }
});

// SAVE progress
app.post('/api/progress', (req, res) => {
  try {
    const data = req.body;
    data.lastActive = new Date().toISOString().split('T')[0];
    fs.writeFileSync(PROGRESS_FILE, JSON.stringify(data, null, 2));
    res.json({ success: true });
  } catch (err) {
    res.status(500).json({ error: 'Failed to save progress' });
  }
});

// Toggle a single task
app.post('/api/toggle', (req, res) => {
  try {
    const { taskId } = req.body;
    const data = JSON.parse(fs.readFileSync(PROGRESS_FILE, 'utf8'));
    const idx = data.completedTasks.indexOf(taskId);
    if (idx === -1) {
      data.completedTasks.push(taskId);
    } else {
      data.completedTasks.splice(idx, 1);
    }
    data.lastActive = new Date().toISOString().split('T')[0];
    fs.writeFileSync(PROGRESS_FILE, JSON.stringify(data, null, 2));
    res.json({ success: true, completed: idx === -1, completedTasks: data.completedTasks });
  } catch (err) {
    res.status(500).json({ error: 'Failed to toggle task' });
  }
});

// Save notes for a day
app.post('/api/notes', (req, res) => {
  try {
    const { dayId, note } = req.body;
    const data = JSON.parse(fs.readFileSync(PROGRESS_FILE, 'utf8'));
    if (!data.notes) data.notes = {};
    data.notes[dayId] = note;
    data.lastActive = new Date().toISOString().split('T')[0];
    fs.writeFileSync(PROGRESS_FILE, JSON.stringify(data, null, 2));
    res.json({ success: true });
  } catch (err) {
    res.status(500).json({ error: 'Failed to save note' });
  }
});

app.listen(PORT, () => {
  console.log(`\n  🚀 DSA Progress Tracker running at:`);
  console.log(`  ➜  http://localhost:${PORT}\n`);
});
