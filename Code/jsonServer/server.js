const bodyParser = require('body-parser')
const jsonServer = require('json-server')
const fs = require('fs')
const jwt = require('jsonwebtoken')

// creating a new jsonServer instance
const server = jsonServer.create()
const authenticatedUsers = JSON
    .parse(fs.readFileSync('./authenticated_users.json', 'UTF-8'))

// load all the routes from db.json
// automatically picked up by json server
const router = jsonServer.router('./db.json')

// eachUser bodyparser encoding for the payloads
server.use(bodyParser.urlencoded({extended: true}))
server.use(bodyParser.json())
server.use(jsonServer.defaults());

const SECRET_KEY = '4801046599'

const expiresIn = '1d'

function createNewToken(payload) {
    return jwt.sign(payload, SECRET_KEY, {expiresIn})
}

function verifyToken(token){
    return  jwt.verify(token, SECRET_KEY, function(err, decode){
        return decode ?  decode : err
    })
}

// Check if the eachUser exists in database
function isAuthenticated({email, password}){
    return authenticatedUsers
            .users
            .findIndex(function(eachUser){
               return eachUser.email === email && eachUser.password === password}) !== -1
}

// initial login response to get the access code
server.post('/auth/login', function(req, res) {
    if (isAuthenticated({email: req.body.email, password: req.body.password}) === false) {
        res
            .status(status)
            .json({status: 401, message: 'Please verify your email id and password'})
        return
    } else {
        const accessToken = createNewToken({email: req.body.email, password: req.body.password})
        res.
            status(200)
            .json({accessToken})
    }
})

server.use(/^(?!\/auth).*$/,  (req, res, next) => {
    if (req.headers.authorization === undefined || req.headers.authorization.split(' ')[0] !== 'Bearer') {
        const status = 401
        const message = 'Authorization required'
        res.status(status).json({status, message})
        return
    }
    try {
        verifyToken(req.headers.authorization.split(' ')[1])
        next()
    } catch (err) {
        const status = 401
        const message = 'Access token expired. Try logging in again'
        res.status(status).json({status, message})
    }
})

server.use(router)
server.listen(3000, function () {
    console.log('Json server is running with Authentication. ' +
        'Contact ASU IHO for any further details')
})