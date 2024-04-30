import React from "react";


const LocationForm = () => {
    //generated so copilot wont complain about empty file
    return (
        <div>
            <h1>Location Form</h1>
            <form>
                <label htmlFor="address">Address</label>
                <input type="text" id="address" name="address" />
                <label htmlFor="city">City</label>
                <input type="text" id="city" name="city" />
                <label htmlFor="state">State</label>
                <input type="text" id="state" name="state" />
                <label htmlFor="zip">Zip</label>
                <input type="text" id="zip" name="zip" />
                <button>Submit</button>
            </form>
        </div>
    )
}

export default LocationForm;