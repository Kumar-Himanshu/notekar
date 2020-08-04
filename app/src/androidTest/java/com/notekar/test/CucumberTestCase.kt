package com.notekar.test


/**
 * Created by Kumar Himanshu(KHimanshu@ustechsolutions.com) on 20-07-2020.
 * Copyright (c) 2020 USTech Solutions. All rights reserved.
 */

import cucumber.api.CucumberOptions

@CucumberOptions(
    features = ["features"],
    glue = ["com.sniper.bdd.cucumber.steps"],
    tags = ["@e2e", "@smoke"]
)
@SuppressWarnings("unused")
class CucumberTestCase