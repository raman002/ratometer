"use strict";

$(document).ready(function () {
    let URL = window.location.href;
    let options = {
        responsive: true,
        fixedHeader: true
    };

    if (URL.endsWith('assign-teams'))
        dashboardTables.loadUnassignedMembersTable(options);
    else if (URL.endsWith('members'))
        dashboardTables.loadMembersTable(options);
    else if (URL.endsWith('teams'))
        dashboardTables.loadTeamTable(options);

});

function getBlockUIProperties() {
    return {
        css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .5,
            color: '#ffffff'
        },
        message: '<h5>Please wait...</h5>'
    };
}

let userService = {

    validate: function() {
            let expectedRatings = 16;
            let receivedRatings;
            let validator = {
                dataArray: [],
                getData: function () {
                    return this.dataArray;
                }
            };
            let quarter = $('#quarter').val();

        if (quarter === '0') {
            alert('Please select a quarter!');
            $.unblockUI();
            return;
        }

        $('input[type="radio"]').each(function (index, option) {
                if (option && option.checked) {

                    let $option = $(option);
                    let optionUid = $option.attr("id");
                    let orderId = $option.attr("value");

                    validator.dataArray.push({
                        'optionUid': optionUid,
                        'orderId': orderId,
                        'quarter': quarter
                    })
                }
            });

            receivedRatings = validator.dataArray.length;


             if (expectedRatings != receivedRatings) {
                 $.unblockUI();
                alert(`Please enter all the ratings!\nExpected Ratings: ${expectedRatings}\nReceived Ratings: ${receivedRatings}`);
                return;
            }

       return validator;
    },

    submitRating: function (message) {

        $.blockUI(getBlockUIProperties());

        let dataArr = this.validate() ? this.validate().getData() : [];

        if (dataArr.length == 0) return;

        $.ajax({
            url: "/dashboard/submit-user-ratings",
            method: "post",
            data: {'data' : JSON.stringify(dataArr)},
        })
            .done(function (response) {
            })
            .always(function (jsonResponse) {
                $.unblockUI();

                if (jsonResponse) {
                    if (parseInt(jsonResponse.code) == 200) {
                        alert(jsonResponse.message);
                        window.location.href = '/dashboard?intro';
                    } else {
                        alert('Something went wrong while submitting ratings!');
                    }
                } else {
                    alert('Something went wrong while submitting ratings!');
                }
            });
    }
}

let dashboardService = {
    assignTeams: function () {
        let dataArray = [];

        $.blockUI(getBlockUIProperties());

        $('select').each(function (index, select) {
            let $select = $(select);
            let userId = $select.attr("id");

            if (!userId) return;
            let teamId = null;

            $select.children().each(function (index, option) {
                if (index == 0) return;

                if (option.selected) {
                    teamId = option.value;
                    dataArray.push({userId, teamId});
                }
            });

        });

        if (dataArray.length == 0) {
            $.unblockUI();
            alert("Please choose a member to assign!")
            return;
        }

        $.ajax({
            url: "/dashboard/assign-teams",
            method: "post",
            data: {'data': JSON.stringify(dataArray)},
        })
            .done(function (response) {
            })
            .always(function (jsonResponse) {
                $.unblockUI();

                if (jsonResponse) {
                    if (parseInt(jsonResponse.code) == 200) {
                        alert(jsonResponse.message);
                        window.location.href = '/dashboard?assign-teams';
                    } else {
                        alert('Something went wrong while assigning teams!');
                    }
                } else {
                    alert('Something went wrong while assigning teams!');
                }
            });
    }
}

let dashboardTables = {

    loadTeamTable: function (options) {
        $('#teams').DataTable(options);

        // Adding save button;
        $('#teams_filter')
            .append($('<button type="button" data-target="#add_team_modal" data-toggle="modal" class="btn btn-success ml-3 btn-sm"><i class="fa fa-plus mr-1"></i>Add</button>'));
    },

    loadMembersTable: function (options) {
        $('#members').DataTable(options);
    },

    loadUnassignedMembersTable: function (options) {
        $('#teamAssignment').DataTable(options);
        // Adding save button;
        $('#teamAssignment_filter')
            .append($('<button type="button" onclick="dashboardService.assignTeams()" class="btn btn-success ml-3 btn-sm"><i class="fa fa-save mr-1"></i>Save</button>'));
    }
}

let teamService = {

    createTeam: function () {

        let teamName = $('#teamName').val().trim();

        if (teamName === '') {
            alert("Please enter a valid team name!");
            return;
        }

        $.blockUI(getBlockUIProperties());

        $.ajax({
            url: '/dashboard/create-team',
            method: 'post',
            data: {
                'data': JSON.stringify({'teamName': teamName})
            }

        }).always(function (response) {
            if (response && response.code) {
                let responseCode = response.code;

                $.unblockUI();

                if (responseCode == 200) {
                    alert('Team is created successfully!');
                    window.location.href = '?teams';
                } else if (responseCode == 409) {
                    alert(teamName + ' already exists!');
                }
            }
        })
    }
}
