#!/bin/bash

pid=$(ps -ef | grep school | grep -v grep | awk '{print $2}')

if [ ! -z "$pid" ]; then
  kill -9 $pid
fi