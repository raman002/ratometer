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
    saveRating: function () {


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
            dataType: 'application/json',
            data: {'data': JSON.stringify(dataArray)},
        })
        .done(function (response) {
        })
        .always(function (response) {
            $.unblockUI();
            let jsonResponse = JSON.parse(response.responseText);

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
    },

    loadMembersTable: function (options) {
        $('#members').DataTable(options);
    },

    loadUnassignedMembersTable: function (options) {
        $('#navTabContentMembers').DataTable(options);
        // Adding save button;
        $('#navTabContentMembers_filter')
            .append($('<button type="button" onclick="dashboardService.assignTeams()" class="btn btn-success ml-3 btn-sm"><i class="fa fa-save mr-1"></i>Save</button>'));
    }
}